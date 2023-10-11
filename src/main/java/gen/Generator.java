package gen;

import inter.*;
import parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Generator {

    private final Parser parse;
    private PrintStream out;
    private int labels = 0;
    private int tempCount = 0;

    private int afterEnclosing = 0; // used for break stmts

    public Generator(Parser parse) {
        this.parse = parse;
    }

    public List<String> gen() {
        final var buffer = new ByteArrayOutputStream();
        out = new PrintStream(buffer);
        final var program = parse.program();

        int begin = newlabel();
        int after = newlabel();
        emitlabel(begin);
        genStmt(program, begin, after);
        emitlabel(after);

        return Arrays.asList(buffer.toString().split("\r?\n"));
    }

    public void genStmt(Stmt stmt, int begin, int after) {
        if (stmt instanceof Seq seq) {
            genSeq(seq, begin, after);
        } else if (stmt instanceof Set set) {
            genSet(set);
        } else if (stmt instanceof SetElem setElem) {
            genSetElem(setElem);
        } else if (stmt instanceof If ifStmt) {
            genIf(ifStmt, after);
        } else if (stmt instanceof Else elseStmt) {
            genElse(elseStmt, after);
        } else if (stmt instanceof While whileLoop) {
            genWhile(whileLoop, begin, after);
        } else if (stmt instanceof Do doLoop) {
            genDo(doLoop, begin, after);
        } else if (stmt instanceof Break) {
            genBreak();
        }
    } // called with labels begin and after

    public void genSeq(Seq seq, int begin, int after) {
        if (seq.head() == Stmt.NULL) genStmt(seq.tail(), begin, after);
        else if (seq.tail() == Stmt.NULL) genStmt(seq.head(), begin, after);
        else {
            final int label = newlabel();
            genStmt(seq.head(), begin, label);
            emitlabel(label);
            genStmt(seq.tail(), label, after);
        }
    }

    public void genSet(Set set) {
        emit(set.id() + " = " + genExpr(set.expr()));
    }

    public void genSetElem(SetElem setElem) {
        final String s1 = reduce(setElem.index()).toString();
        final String s2 = reduce(setElem.expr()).toString();
        emit(setElem.array() + " [ " + s1 + " ] = " + s2);
    }

    public void genIf(If ifStmt, int after) {
        final int label = newlabel(); // label for the code for stmt
        jumping(ifStmt.expr(), 0, after); // fall through on true, goto a on false
        emitlabel(label);
        genStmt(ifStmt.stmt(), label, after);
    }

    public void genElse(Else elseStmt, int after) {
        final int label1 = newlabel(); // label1 for stmt1
        final int label2 = newlabel(); // label2 for stmt2
        jumping(elseStmt.expr(), 0, label2); // fall through to stmt1 on true
        emitlabel(label1);
        genStmt(elseStmt.stmt1(), label1, after);
        emit("goto L" + after);
        emitlabel(label2);
        genStmt(elseStmt.stmt2(), label2, after);
    }

    public void genWhile(While whileLoop, int begin, int after) {
        final var saved = afterEnclosing;
        afterEnclosing = after; // save label after
        jumping(whileLoop.expr(), 0, after);
        int label = newlabel(); // label for stmt
        emitlabel(label);
        genStmt(whileLoop.stmt(), label, begin);
        emit("goto L" + begin);
        afterEnclosing = saved;
    }

    public void genDo(Do doLoop, int begin, int after) {
        final var saved = afterEnclosing;
        afterEnclosing = after; // save label after
        final int label = newlabel(); // label for expr
        genStmt(doLoop.stmt(), begin, label);
        emitlabel(label);
        jumping(doLoop.expr(), begin, 0);
        afterEnclosing = saved;
    }

    public void genBreak() {
        emit("goto L" + afterEnclosing);
    }

    public Expr genExpr(Expr expr) {
        return switch (expr) {
            case Logical logical -> genLogical(logical);
            case Arith arith -> genArith(arith);
            case Unary unary -> genUnary(unary);
            case Access access -> genAccess(access);
            default -> expr;
        };
    }

    public Expr genLogical(Logical logical) {
        final int f = newlabel();
        final int a = newlabel();
        final var temp = new Temp(logical.type(), ++tempCount);
        jumping(logical, 0, f);
        emit(temp + " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(temp + " = false");
        emitlabel(a);
        return temp;
    }

    public Expr genArith(Arith arith) {
        return new Arith(arith.op(), reduce(arith.expr1()), reduce(arith.expr2()));
    }

    public Expr genUnary(Unary unary) {
        return new Unary(reduce(unary.expr()));
    }

    public Expr genAccess(Access access) {
        return new Access(access.array(), reduce(access.index()), access.type());
    }

    public Expr reduce(Expr expr) {
        if (expr instanceof Op op) {
            return reduceOp(op);
        }

        return expr;
    }

    public Expr reduceOp(Op op) {
        final Expr expr = genExpr(op);
        final Temp temp = new Temp(op.type(), ++tempCount);
        emit(temp + " = " + expr);
        return temp;
    }

    public void jumping(Expr expr, int t, int f) {
        switch (expr) {
            case Constant constant -> jumpingConstant(constant, t, f);
            case And and -> jumpingAnd(and, t, f);
            case Or or -> jumpingOr(or, t, f);
            case Not not -> jumpingNot(not, t, f);
            case Rel rel -> jumpingRel(rel, t, f);
            case Access access -> jumpingAccess(access, t, f);
            default -> emitJumps(expr.toString(), t, f);
        }
    }

    public void jumpingConstant(Constant constant, int t, int f) {
        if (constant == Constant.TRUE && t != 0) emit("goto L" + t);
        else if (constant == Constant.FALSE && f != 0) emit("goto L" + f);
    }

    public void jumpingAnd(And and, int t, int f) {
        final int label = f != 0 ? f : newlabel();
        jumping(and.expr1(), 0, label);
        jumping(and.expr2(), t, f);
        if (f == 0) emitlabel(label);
    }

    public void jumpingOr(Or or, int t, int f) {
        final int label = t != 0 ? t : newlabel();
        jumping(or.expr1(), label, 0);
        jumping(or.expr2(), t, f);
        if (t == 0) emitlabel(label);
    }

    public void jumpingRel(Rel rel, int t, int f) {
        final String test = reduce(rel.expr1()) + " " + rel.op() + " " + reduce(rel.expr2());
        emitJumps(test, t, f);
    }

    public void jumpingNot(Not not, int t, int f) {
        jumping(not.expr2(), f, t);
    }

    public void jumpingAccess(Access access, int t, int f) {
        emitJumps(reduce(access).toString(), t, f);
    }

    public void emitJumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        } else if (t != 0) emit("if " + test + " goto L" + t);
        else if (f != 0) emit("iffalse " + test + " goto L" + f);
        // nothing since both t and f fall through
    }

    public int newlabel() {
        return ++labels;
    }

    public void emitlabel(int i) {
        out.print("L" + i + ":");
    }

    public void emit(String s) {
        out.println("\t" + s);
    }
}