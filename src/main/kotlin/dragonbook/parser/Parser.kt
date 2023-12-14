package dragonbook.parser

import dragonbook.inter.expr.*
import dragonbook.inter.stmt.*
import dragonbook.inter.stmt.Set
import dragonbook.lexer.*
import dragonbook.symbols.Array
import dragonbook.symbols.Env
import dragonbook.symbols.Type

class Parser(private val scan: () -> Token) {

    constructor(lexer: Lexer) : this(lexer::scan) // lexical analyzer for this parser

    private lateinit var look: Token // lookahead token
    private var top: Env? = null // current or top symbol table
    private var enclosing: Word? = null // used for break stmts
    private var used = 0 // storage used for declarations

    init {
        move()
    }

    fun program(): Stmt { // program -> block
        return block()
    }

    private fun block(): Stmt { // block -> { decls stmts }
        match('{')
        val savedEnv = top
        top = Env(top)
        decls()
        val stmts = stmts()
        match('}')
        top = savedEnv
        return stmts
    }

    private fun decls() {
        while (look.tag == Tag.BASIC) { // D -> type ID 
            val type = type()
            val tok = look
            match(Tag.ID)
            match(';')
            val id = Id(tok as Word, type, used)
            top!![tok] = id
            used += type.width
        }
    }

    private fun type(): Type {
        val type = look as Type // expect look.tag == Tag.BASIC
        match(Tag.BASIC)
        return if (look.tag != '['.code) type // T -> basic
        else dims(type) // return array type
    }

    private fun dims(type: Type): Type {
        match('[')
        val tok = look
        match(Tag.NUM)
        match(']')
        return Array(size = (tok as Num).value, of = if (look.tag == '['.code) dims(type) else type)
    }

    private fun stmts(): Stmt {
        return if (look.tag == '}'.code) Stmt.NULL
        else Seq(stmt(), stmts())
    }

    private fun stmt(): Stmt {
        return when (look.tag) {
            ';'.code -> {
                move()
                Stmt.NULL
            }

            Tag.IF -> ifElse()
            Tag.WHILE -> whileLoop()
            Tag.DO -> doLoop()
            Tag.BREAK -> breakStmt()
            '{'.code -> block()
            else -> assign()
        }
    }

    private fun ifElse(): Stmt {
        match(Tag.IF)
        match('(')
        val condition = bool()
        match(')')
        val then = stmt()
        if (look.tag != Tag.ELSE) return If(condition, then)
        match(Tag.ELSE)
        val elseStmt = stmt()
        return Else(condition, then, elseStmt)
    }

    private fun whileLoop(): While {
        val savedStmt = enclosing // save enclosing loop for breaks
        enclosing = Word.WHILE
        match(Tag.WHILE)
        match('(')
        val condition = bool()
        match(')')
        val stmt = stmt()
        val whileLoop = While(condition, stmt)
        enclosing = savedStmt // reset Enclosing
        return whileLoop
    }

    private fun doLoop(): Do {
        val savedStmt = enclosing // save enclosing loop for breaks
        enclosing = Word.DO
        match(Tag.DO)
        val stmt = stmt()
        match(Tag.WHILE)
        match('(')
        val condition = bool()
        match(')')
        match(';')
        val doLoop = Do(stmt, condition)
        enclosing = savedStmt // reset Enclosing
        return doLoop
    }

    private fun breakStmt(): Break {
        match(Tag.BREAK)
        match(';')
        if (enclosing == null) error("unenclosed break")
        return Break
    }

    private fun assign(): Stmt {
        val t = look
        match(Tag.ID)
        val id = top!![t] ?: error("$t undeclared")
        val stmt: Stmt =
            if (look.tag == '='.code) { // S -> id = E
                move()
                Set(id, bool())
            } else { // S -> L = E
                val access = offset(id)
                match('=')
                SetElem(access, bool())
            }
        match(';')
        return stmt
    }

    private fun bool(): Expr {
        var expr = join()
        while (look.tag == Tag.OR) {
            val tok = look
            move()
            expr = Or(tok, expr, join())
        }
        return expr
    }

    private fun join(): Expr {
        var expr = equality()
        while (look.tag == Tag.AND) {
            val tok = look
            move()
            expr = And(tok, expr, equality())
        }
        return expr
    }

    private fun equality(): Expr {
        var expr = rel()
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            val tok = look
            move()
            expr = Rel(tok, expr, rel())
        }
        return expr
    }

    private fun rel(): Expr {
        val expr = expr()
        return when (look.tag) {
            '<'.code,
            Tag.LE,
            Tag.GE,
            '>'.code -> {
                val tok = look
                move()
                Rel(tok, expr, expr())
            }

            else -> expr
        }
    }

    private fun expr(): Expr {
        var expr = term()
        while (look.tag == '+'.code || look.tag == '-'.code) {
            val tok = look
            move()
            expr = Arith(tok, expr, term())
        }
        return expr
    }

    private fun term(): Expr {
        var expr = unary()
        while (look.tag == '*'.code || look.tag == '/'.code) {
            val tok = look
            move()
            expr = Arith(tok, expr, unary())
        }
        return expr
    }

    private fun unary(): Expr {
        return when (look.tag) {
            '-'.code -> {
                move()
                Unary(Word.MINUS, unary())
            }

            '!'.code -> {
                val tok = look
                move()
                Not(tok, unary())
            }

            else -> factor()
        }
    }

    private fun factor(): Expr {
        return when (look.tag) {
            '('.code -> {
                move()
                val expr = bool()
                match(')')
                expr
            }

            Tag.NUM -> {
                val expr = Constant(look, Type.INT)
                move()
                expr
            }

            Tag.REAL -> {
                val expr = Constant(look, Type.FLOAT)
                move()
                expr
            }

            Tag.TRUE -> {
                val expr = Constant.TRUE
                move()
                expr
            }

            Tag.FALSE -> {
                val expr = Constant.FALSE
                move()
                expr
            }

            Tag.ID -> {
                val id = top!![look] ?: error("$look undeclared")
                move()
                if (look.tag != '['.code) id else offset(id)
            }

            else -> error("syntax error")
        }
    }

    private fun offset(id: Id): Access { // I -> [E] | [E] I
        var index: Expr
        var width: Expr
        var times: Expr
        var plus: Expr
        var loc: Expr // inherit id
        var type = id.type
        match('[')
        index = bool()
        match(']') // first index, I -> [ E ]
        type = (type as Array).of
        width = Constant(type.width)
        times = Arith(Token.TIMES, index, width)
        loc = times
        while (look.tag == '['.code) { // multi-dimensional I -> [ E ] I
            match('[')
            index = bool()
            match(']')
            type = (type as Array).of
            width = Constant(type.width)
            times = Arith(Token.TIMES, index, width)
            plus = Arith(Token.PLUS, loc, times)
            loc = plus
        }
        return Access(id, loc, type)
    }

    private fun move() {
        look = scan()
    }

    private fun error(s: String): Nothing {
        throw Error("near line 0: $s")
    }

    private fun match(tok: Char) {
        match(tok.code)
    }

    private fun match(tok: Int) {
        if (look.tag == tok) move()
        else error("syntax error")
    }
}