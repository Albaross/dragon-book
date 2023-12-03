package gen;

import inter.*;
import inter.Set;
import parser.*;

import java.io.*;
import java.util.*;

public class Generator {

    private final Parser parse;
    private static PrintStream out = System.out;
    private static int labels = 0;

    public Generator(Parser parse) {
        this.parse = parse;
    }

    public List<String> gen() throws IOException {
        final var buffer = new ByteArrayOutputStream();
        out = new PrintStream(buffer);
        parse.program();
        return Arrays.asList(buffer.toString().split("\r?\n"));
    }

    public static void genSeq(Seq seq, int begin, int after) {
        if (seq.stmt1 == Stmt.Null) seq.stmt2.gen(begin, after);
        else if (seq.stmt2 == Stmt.Null) seq.stmt1.gen(begin, after);
        else {
            int label = newlabel();
            seq.stmt1.gen(begin, label);
            emitlabel(label);
            seq.stmt2.gen(label, after);
        }
    }

    public static void genSet(Set set, int begin, int after) {
        emit(set.id.toString() + " = " + set.expr.gen().toString());
    }

    public static void genSetElem(SetElem setElem, int begin, int after) {
        String s1 = setElem.index.reduce().toString();
        String s2 = setElem.expr.reduce().toString();
        emit(setElem.array.toString() + " [ " + s1 + " ] = " + s2);
    }

    public static void genIf(If ifStmt, int begin, int after) {
        int label = newlabel(); // label for the code for stmt
        ifStmt.expr.jumping(0, after); // fall through on true, goto a on false
        emitlabel(label);
        ifStmt.stmt.gen(label, after);
    }

    public static void genElse(Else elseStmt, int begin, int after) {
        int label1 = newlabel(); // label1 for stmt1
        int label2 = newlabel(); // label2 for stmt2
        elseStmt.expr.jumping(0, label2); // fall through to stmt1 on true
        emitlabel(label1);
        elseStmt.stmt1.gen(label1, after);
        emit("goto L" + after);
        emitlabel(label2);
        elseStmt.stmt2.gen(label2, after);
    }

    public static void genWhile(While whileLoop, int begin, int after) {
        whileLoop.after = after; // save label a
        whileLoop.expr.jumping(0, after);
        int label = newlabel(); // label for stmt
        emitlabel(label);
        whileLoop.stmt.gen(label, begin);
        emit("goto L" + begin);
    }


    public static void genDo(Do doLoop, int begin, int after) {
        doLoop.after = after;
        int label = newlabel(); // label for expr
        doLoop.stmt.gen(begin, label);
        emitlabel(label);
        doLoop.expr.jumping(begin, 0);
    }

    public static void genBreak(Break breakStmt, int begin, int after) {
        emit("goto L" + breakStmt.stmt.after);
    }

    public static Expr genLogical(Logical logical) {
        int f = newlabel();
        int a = newlabel();
        Temp temp = new Temp(logical.type);
        logical.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(temp.toString() + " = false");
        emitlabel(a);
        return temp;
    }


    public static Expr genArith(Arith arith) {
        return new Arith(arith.op, arith.expr1.reduce(), arith.expr2.reduce());
    }

    public static Expr genUnary(Unary unary) {
        return new Unary(unary.op, unary.expr.reduce());
    }

    public static Expr genAccess(Access access) {
        return new Access(access.array, access.index.reduce(), access.type);
    }

    public static Expr genExpr(Expr expr) {
        return expr;
    }

    public static Expr reduceOp(Op op) {
        Expr x = op.gen();
        Temp t = new Temp(op.type);
        emit(t.toString() + " = " + x.toString());
        return t;
    }

    public static Expr reduceExpr(Expr expr) {
        return expr;
    }

    public static void jumpingConstant(Constant constant, int t, int f) {
        if (constant == Constant.True && t != 0) emit("goto L" + t);
        else if (constant == Constant.False && f != 0) emit("goto L" + f);
    }

    public static void jumpingAnd(And and, int t, int f) {
        int label = f != 0 ? f : newlabel();
        and.expr1.jumping(0, label);
        and.expr2.jumping(t, f);
        if (f == 0) emitlabel(label);
    }

    public static void jumpingOr(Or or, int t, int f) {
        int label = t != 0 ? t : newlabel();
        or.expr1.jumping(label, 0);
        or.expr2.jumping(t, f);
        if (t == 0) emitlabel(label);
    }

    public static void jumpingRel(Rel rel, int t, int f) {
        Expr a = rel.expr1.reduce();
        Expr b = rel.expr2.reduce();

        String test = a.toString() + " " + rel.op.toString() + " " + b.toString();
        emitjumps(test, t, f);
    }

    public static void jumpingNot(Not not, int t, int f) {
        not.expr2.jumping(f, t);
    }

    public static void jumpingAccess(Access access, int t, int f) {
        emitjumps(access.reduce().toString(), t, f);
    }

    public static void jumpingExpr(Expr expr, int t, int f) {
        emitjumps(expr.toString(), t, f);
    }

    public static void emitjumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        } else if (t != 0) emit("if " + test + " goto L" + t);
        else if (f != 0) emit("iffalse " + test + " goto L" + f);
        else ; // nothing since both t and f fall through
    }

    public static int newlabel() {
        return ++labels;
    }

    public static void emitlabel(int i) {
        out.print("L" + i + ":");
    }

    public static void emit(String s) {
        out.println("\t" + s);
    }
}