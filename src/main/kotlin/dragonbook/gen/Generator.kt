package dragonbook.gen

import dragonbook.inter.expr.*
import dragonbook.inter.stmt.*
import dragonbook.inter.stmt.Set
import dragonbook.lexer.Num
import dragonbook.lexer.Real
import dragonbook.lexer.Token
import dragonbook.lexer.Word
import dragonbook.parser.Parser
import dragonbook.symbols.Array
import java.io.ByteArrayOutputStream

class Generator(private val program: () -> Stmt) {

    constructor(parser: Parser) : this(parser::program)

    private val buffer = ByteArrayOutputStream()
    private var labels = 0
    private var tempCount = 0
    private var afterEnclosing = 0 // saves label after

    fun gen(): List<String> {
        val program = program()

        val begin = newlabel()
        val after = newlabel()
        emitlabel(begin)
        genStmt(program, begin, after)
        emitlabel(after)

        return buffer.toString().split('\n').toList()
    }

    private fun genStmt(stmt: Stmt, begin: Int, after: Int) {
        when (stmt) {
            is Seq -> genSeq(stmt, begin, after)
            is Set -> genSet(stmt)
            is SetElem -> genSetElem(stmt)
            is If -> genIf(stmt, after)
            is Else -> genElse(stmt, after)
            is While -> genWhile(stmt, begin, after)
            is Do -> genDo(stmt, begin, after)
            is Break -> genBreak()
            else -> throw IllegalArgumentException("Unexpected value: $stmt")
        }
    } // called with labels begin and after


    private fun genSeq(seq: Seq, begin: Int, after: Int) {
        if (seq.head == Stmt.Null) genStmt(seq.tail, begin, after)
        else if (seq.tail == Stmt.Null) genStmt(seq.head, begin, after)
        else {
            val label = newlabel()
            genStmt(seq.head, begin, label)
            emitlabel(label)
            genStmt(seq.tail, label, after)
        }
    }

    private fun genSet(set: Set) {
        emit(str(set.id) + " = " + str(genExpr(set.expr)))
    }

    private fun genSetElem(setElem: SetElem) {
        val s1 = str(reduce(setElem.index))
        val s2 = str(reduce(setElem.expr))
        emit(str(setElem.array) + " [ " + s1 + " ] = " + s2)
    }

    private fun genIf(ifStmt: If, after: Int) {
        val label = newlabel() // label for the code for stmt
        jumping(ifStmt.condition, 0, after) // fall through on true, goto after on false
        emitlabel(label)
        genStmt(ifStmt.then, label, after)
    }

    private fun genElse(elseStmt: Else, after: Int) {
        val label1 = newlabel() // label1 for stmt1
        val label2 = newlabel() // label2 for stmt2
        jumping(elseStmt.condition, 0, label2) // fall through to stmt1 on true
        emitlabel(label1)
        genStmt(elseStmt.then, label1, after)
        emit("goto L$after")
        emitlabel(label2)
        genStmt(elseStmt.elseStmt, label2, after)
    }

    private fun genWhile(whileLoop: While, begin: Int, after: Int) {
        val saved = afterEnclosing
        afterEnclosing = after // save label after
        jumping(whileLoop.condition, 0, after)
        val label = newlabel() // label for stmt
        emitlabel(label)
        genStmt(whileLoop.stmt, label, begin)
        emit("goto L$begin")
        afterEnclosing = saved
    }


    private fun genDo(doLoop: Do, begin: Int, after: Int) {
        val saved = afterEnclosing
        afterEnclosing = after
        val label = newlabel() // label for expr
        genStmt(doLoop.stmt, begin, label)
        emitlabel(label)
        jumping(doLoop.condition, begin, 0)
        afterEnclosing = saved
    }

    private fun genBreak() {
        emit("goto L$afterEnclosing")
    }

    private fun genExpr(expr: Expr): Expr {
        return when (expr) {
            is Logical -> genLogical(expr)
            is Arith -> genArith(expr)
            is Unary -> genUnary(expr)
            is Access -> genAccess(expr)
            else -> expr
        }
    }

    private fun genLogical(logical: Logical): Expr {
        val f = newlabel()
        val a = newlabel()
        val temp = Temp(logical.type, ++tempCount)
        jumping(logical, 0, f)
        emit(str(temp) + " = true")
        emit("goto L$a")
        emitlabel(f)
        emit(str(temp) + " = false")
        emitlabel(a)
        return temp
    }


    private fun genArith(arith: Arith): Expr {
        return Arith(arith.op, reduce(arith.expr1), reduce(arith.expr2))
    }

    private fun genUnary(unary: Unary): Expr {
        return Unary(unary.op, reduce(unary.expr))
    }

    private fun genAccess(access: Access): Expr {
        return Access(access.array, reduce(access.index), access.type)
    }

    private fun reduce(expr: Expr): Expr {
        if (expr is Op) {
            return reduceOp(expr)
        }

        return expr
    }

    private fun reduceOp(op: Op): Expr {
        val expr = genExpr(op)
        val temp = Temp(op.type, ++tempCount)
        emit(str(temp) + " = " + str(expr))
        return temp
    }

    private fun jumping(expr: Expr, t: Int, f: Int) {
        when (expr) {
            is Constant -> jumpingConstant(expr, t, f)
            is And -> jumpingAnd(expr, t, f)
            is Or -> jumpingOr(expr, t, f)
            is Not -> jumpingNot(expr, t, f)
            is Rel -> jumpingRel(expr, t, f)
            is Access -> jumpingAccess(expr, t, f)
            else -> emitjumps(str(expr), t, f)
        }
    }

    private fun jumpingConstant(constant: Constant, t: Int, f: Int) {
        if (constant == Constant.TRUE && t != 0) emit("goto L$t")
        else if (constant == Constant.FALSE && f != 0) emit("goto L$f")
    }

    private fun jumpingAnd(and: And, t: Int, f: Int) {
        val label = if (f != 0) f else newlabel()
        jumping(and.expr1, 0, label)
        jumping(and.expr2, t, f)
        if (f == 0) emitlabel(label)
    }

    private fun jumpingOr(or: Or, t: Int, f: Int) {
        val label = if (t != 0) t else newlabel()
        jumping(or.expr1, label, 0)
        jumping(or.expr2, t, f)
        if (t == 0) emitlabel(label)
    }

    private fun jumpingRel(rel: Rel, t: Int, f: Int) {
        val a = reduce(rel.expr1)
        val b = reduce(rel.expr2)

        val test = str(a) + " " + str(rel.op) + " " + str(b)
        emitjumps(test, t, f)
    }

    private fun jumpingNot(not: Not, t: Int, f: Int) {
        jumping(not.expr2, f, t)
    }

    private fun jumpingAccess(access: Access, t: Int, f: Int) {
        emitjumps(str(reduce(access)), t, f)
    }

    private fun emitjumps(test: String, t: Int, f: Int) {
        if (t != 0 && f != 0) {
            emit("if $test goto L$t")
            emit("goto L$f")
        } else if (t != 0) emit("if $test goto L$t")
        else if (f != 0) emit("iffalse $test goto L$f")
        // nothing since both t and f fall through
    }

    private fun newlabel(): Int {
        return ++labels
    }

    private fun emitlabel(i: Int) {
        buffer.write("L$i:".toByteArray())
    }

    private fun emit(s: String) {
        buffer.write("\t$s\n".toByteArray())
    }

    private fun str(expr: Expr): String {
        return when (expr) {
            is Access -> str(expr.array) + " [ " + str(expr.index) + " ]"
            is Arith -> str(expr.expr1) + " " + str(expr.op) + " " + str(expr.expr2)
            is Not -> str(expr.op) + " " + str(expr.expr2)
            is Logical -> str(expr.expr1) + " " + str(expr.op) + " " + str(expr.expr2)
            is Temp -> "t" + expr.number
            is Unary -> str(expr.op) + " " + str(expr.expr)
            else -> str(expr.op)
        }
    }

    private fun str(op: Token): String {
        return when (op) {
            is Array -> "[" + op.size + "] " + str(op.of)
            is Num -> op.value.toString()
            is Real -> op.value.toString()
            is Word -> op.lexeme
            else -> op.tag.toChar().toString()
        }
    }
}