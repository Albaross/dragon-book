package dragonbook.parser;

import dragonbook.inter.expr.*;
import dragonbook.inter.stmt.*;
import dragonbook.lexer.*;
import dragonbook.symbols.Array;
import dragonbook.symbols.Env;
import dragonbook.symbols.Type;

public class Parser {
    private final Lexer lex; // lexical analyzer for this parser
    private Token look; // lookahead token
    private Env top = null; // current or top symbol table
    private Word enclosing = null; // used for break stmts
    private int used = 0; // storage used for declarations

    public Parser(Lexer lex) {
        this.lex = lex;
        move();
    }

    public Stmt program() { // program -> block
        return block();
    }

    private Stmt block() { // block -> { decls stmts }
        match('{');
        final Env savedEnv = top;
        top = new Env(top);
        decls();
        final Stmt stmts = stmts();
        match('}');
        top = savedEnv;
        return stmts;
    }

    private void decls() {
        while (look.tag == Tag.BASIC) { // D -> type ID ;
            final Type type = type();
            final Token tok = look;
            match(Tag.ID);
            match(';');
            final Id id = new Id((Word) tok, type, used);
            top.put(tok, id);
            used += type.width;
        }
    }

    private Type type() {
        final Type type = (Type) look; // expect look.tag == Tag.BASIC
        match(Tag.BASIC);
        if (look.tag != '[') return type; // T -> basic
        else return dims(type); // return array type
    }

    private Type dims(Type type) {
        match('[');
        final Token tok = look;
        match(Tag.NUM);
        match(']');
        if (look.tag == '[')
            type = dims(type);
        return new Array(((Num) tok).value, type);
    }

    private Stmt stmts() {
        if (look.tag == '}') return Stmt.Null;
        else return new Seq(stmt(), stmts());
    }

    private Stmt stmt() {
        return switch (look.tag) {
            case ';' -> {
                move();
                yield Stmt.Null;
            }
            case Tag.IF -> ifElse();
            case Tag.WHILE -> whileLoop();
            case Tag.DO -> doLoop();
            case Tag.BREAK -> breakStmt();
            case '{' -> block();
            default -> assign();
        };
    }

    private Stmt ifElse() {
        match(Tag.IF);
        match('(');
        final Expr condition = bool();
        match(')');
        final Stmt then = stmt();
        if (look.tag != Tag.ELSE) return new If(condition, then);
        match(Tag.ELSE);
        final Stmt elseStmt = stmt();
        return new Else(condition, then, elseStmt);
    }

    private While whileLoop() {
        final Word savedStmt = enclosing; // save enclosing loop for breaks
        enclosing = Word.WHILE;
        match(Tag.WHILE);
        match('(');
        final Expr condition = bool();
        match(')');
        final Stmt stmt = stmt();
        final While whileLoop = new While(condition, stmt);
        enclosing = savedStmt; // reset Enclosing
        return whileLoop;
    }

    private Do doLoop() {
        final Word savedStmt = enclosing; // save enclosing loop for breaks
        enclosing = Word.DO;
        match(Tag.DO);
        final Stmt stmt = stmt();
        match(Tag.WHILE);
        match('(');
        final Expr condition = bool();
        match(')');
        match(';');
        final Do doLoop = new Do(stmt, condition);
        enclosing = savedStmt; // reset Enclosing
        return doLoop;
    }

    private Break breakStmt() {
        match(Tag.BREAK);
        match(';');
        if (enclosing == null) error("unenclosed break");
        return new Break();
    }

    private Stmt assign() {
        final Token t = look;
        match(Tag.ID);
        final Id id = top.get(t);
        if (id == null) error(t + " undeclared");
        Stmt stmt;
        if (look.tag == '=') { // S -> id = E ;
            move();
            stmt = new Set(id, bool());
        } else { // S -> L = E ;
            Access access = offset(id);
            match('=');
            stmt = new SetElem(access, bool());
        }
        match(';');
        return stmt;
    }

    private Expr bool() {
        Expr expr = join();
        while (look.tag == Tag.OR) {
            final Token tok = look;
            move();
            expr = new Or(tok, expr, join());
        }
        return expr;
    }

    private Expr join() {
        Expr expr = equality();
        while (look.tag == Tag.AND) {
            final Token tok = look;
            move();
            expr = new And(tok, expr, equality());
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = rel();
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            final Token tok = look;
            move();
            expr = new Rel(tok, expr, rel());
        }
        return expr;
    }

    private Expr rel() {
        final Expr expr = expr();
        switch (look.tag) {
            case '<':
            case Tag.LE:
            case Tag.GE:
            case '>':
                final Token tok = look;
                move();
                return new Rel(tok, expr, expr());
            default:
                return expr;
        }
    }

    private Expr expr() {
        Expr expr = term();
        while (look.tag == '+' || look.tag == '-') {
            final Token tok = look;
            move();
            expr = new Arith(tok, expr, term());
        }
        return expr;
    }

    private Expr term() {
        Expr expr = unary();
        while (look.tag == '*' || look.tag == '/') {
            final Token tok = look;
            move();
            expr = new Arith(tok, expr, unary());
        }
        return expr;
    }

    private Expr unary() {
        if (look.tag == '-') {
            move();
            return new Unary(Word.MINUS, unary());
        } else if (look.tag == '!') {
            final Token tok = look;
            move();
            return new Not(tok, unary());
        } else return factor();
    }

    private Expr factor() {
        Expr expr = null;
        switch (look.tag) {
            case '(':
                move();
                expr = bool();
                match(')');
                return expr;
            case Tag.NUM:
                expr = new Constant(look, Type.INT);
                move();
                return expr;
            case Tag.REAL:
                expr = new Constant(look, Type.FLOAT);
                move();
                return expr;
            case Tag.TRUE:
                expr = Constant.TRUE;
                move();
                return expr;
            case Tag.FALSE:
                expr = Constant.FALSE;
                move();
                return expr;
            default:
                error("syntax error");
                return expr;
            case Tag.ID:
                final Id id = top.get(look);
                if (id == null) error(look + " undeclared");
                move();
                if (look.tag != '[') return id;
                else return offset(id);
        }
    }

    private Access offset(Id id) { // I -> [E] | [E] I
        Expr index;
        Expr width;
        Expr times;
        Expr plus;
        Expr loc; // inherit id
        Type type = id.type;
        match('[');
        index = bool();
        match(']'); // first index, I -> [ E ]
        type = ((Array) type).of;
        width = new Constant(type.width);
        times = new Arith(Token.TIMES, index, width);
        loc = times;
        while (look.tag == '[') { // multi-dimensional I -> [ E ] I
            match('[');
            index = bool();
            match(']');
            type = ((Array) type).of;
            width = new Constant(type.width);
            times = new Arith(Token.TIMES, index, width);
            plus = new Arith(Token.PLUS, loc, times);
            loc = plus;
        }
        return new Access(id, loc, type);
    }

    private void move() {
        look = lex.scan();
    }

    private void error(String s) {
        throw new Error("near line " + Lexer.lineNumber + ": " + s);
    }

    private void match(int tok) {
        if (look.tag == tok) move();
        else error("syntax error");
    }
}