package gen;

import inter.*;
import inter.Set;
import lexer.*;
import parser.*;
import symbols.*;

import java.io.*;
import java.util.*;

public class Generator {

    private final Parser parse;
    private PrintStream out;
    private int labels = 0;
    int tempCount = 0;
    int afterEnclosing = 0; // saves label after

    public Generator(Parser parse) {
        this.parse = parse;
    }

    public List<String> gen() throws IOException {
        final var buffer = new ByteArrayOutputStream();
        out = new PrintStream(buffer);
        final var program = parse.program();

        final int begin = newlabel();
        final int after = newlabel();
        emitlabel(begin);
        genStmt(program, begin, after);
        emitlabel(after);

        return Arrays.asList(buffer.toString().split("\r?\n"));
    }

    private void genStmt(Stmt stmt, int begin, int after) {
        switch (stmt) {
            case Seq seq -> genSeq(seq, begin, after);
            case Set set -> genSet(set);
            case SetElem setElem -> genSetElem(setElem);
            case If ifStmt -> genIf(ifStmt, after);
            case Else elseStmt -> genElse(elseStmt, after);
            case While whileLoop -> genWhile(whileLoop, begin, after);
            case Do doLoop -> genDo(doLoop, begin, after);
            case Break ignored -> genBreak();
            default -> throw new IllegalArgumentException("Unexpected value: " + stmt.getClass());
        }
    } // called with labels begin and after


    private void genSeq(Seq seq, int begin, int after) {
        if (seq.stmt1 == Stmt.Null) genStmt(seq.stmt2, begin, after);
        else if (seq.stmt2 == Stmt.Null) genStmt(seq.stmt1, begin, after);
        else {
            int label = newlabel();
            genStmt(seq.stmt1, begin, label);
            emitlabel(label);
            genStmt(seq.stmt2, label, after);
        }
    }

    private void genSet(Set set) {
        emit(str(set.id) + " = " + str(genExpr(set.expr)));
    }

    private void genSetElem(SetElem setElem) {
        String s1 = str(reduce(setElem.index));
        String s2 = str(reduce(setElem.expr));
        emit(str(setElem.array) + " [ " + s1 + " ] = " + s2);
    }

    private void genIf(If ifStmt, int after) {
        int label = newlabel(); // label for the code for stmt
        jumping(ifStmt.expr, 0, after); // fall through on true, goto a on false
        emitlabel(label);
        genStmt(ifStmt.stmt, label, after);
    }

    private void genElse(Else elseStmt, int after) {
        int label1 = newlabel(); // label1 for stmt1
        int label2 = newlabel(); // label2 for stmt2
        jumping(elseStmt.expr, 0, label2); // fall through to stmt1 on true
        emitlabel(label1);
        genStmt(elseStmt.stmt1, label1, after);
        emit("goto L" + after);
        emitlabel(label2);
        genStmt(elseStmt.stmt2, label2, after);
    }

    private void genWhile(While whileLoop, int begin, int after) {
        int saved = afterEnclosing;
        afterEnclosing = after; // save label after
        jumping(whileLoop.expr, 0, after);
        int label = newlabel(); // label for stmt
        emitlabel(label);
        genStmt(whileLoop.stmt, label, begin);
        emit("goto L" + begin);
        afterEnclosing = saved;
    }


    private void genDo(Do doLoop, int begin, int after) {
        int saved = afterEnclosing;
        afterEnclosing = after;
        int label = newlabel(); // label for expr
        genStmt(doLoop.stmt, begin, label);
        emitlabel(label);
        jumping(doLoop.expr, begin, 0);
        afterEnclosing = saved;
    }

    private void genBreak() {
        emit("goto L" + afterEnclosing);
    }

    private Expr genExpr(Expr expr) {
        return switch (expr) {
            case Logical logical -> genLogical(logical);
            case Arith arith -> genArith(arith);
            case Unary unary -> genUnary(unary);
            case Access access -> genAccess(access);
            default -> expr;
        };
    }

    private Expr genLogical(Logical logical) {
        int f = newlabel();
        int a = newlabel();
        Temp temp = new Temp(logical.type, ++tempCount);
        jumping(logical, 0, f);
        emit(str(temp) + " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(str(temp) + " = false");
        emitlabel(a);
        return temp;
    }


    private Expr genArith(Arith arith) {
        return new Arith(arith.op, reduce(arith.expr1), reduce(arith.expr2));
    }

    private Expr genUnary(Unary unary) {
        return new Unary(unary.op, reduce(unary.expr));
    }

    private Expr genAccess(Access access) {
        return new Access(access.array, reduce(access.index), access.type);
    }

    private Expr reduce(Expr expr) {
        if (expr instanceof Op op) {
            return reduceOp(op);
        }

        return expr;
    }

    private Expr reduceOp(Op op) {
        Expr x = genExpr(op);
        Temp t = new Temp(op.type, ++tempCount);
        emit(str(t) + " = " + str(x));
        return t;
    }

    private void jumping(Expr expr, int t, int f) {
        switch (expr) {
            case Constant constant -> jumpingConstant(constant, t, f);
            case And and -> jumpingAnd(and, t, f);
            case Or or -> jumpingOr(or, t, f);
            case Not not -> jumpingNot(not, t, f);
            case Rel rel -> jumpingRel(rel, t, f);
            case Access access -> jumpingAccess(access, t, f);
            default -> emitjumps(str(expr), t, f);
        }
    }

    private void jumpingConstant(Constant constant, int t, int f) {
        if (constant == Constant.True && t != 0) emit("goto L" + t);
        else if (constant == Constant.False && f != 0) emit("goto L" + f);
    }

    private void jumpingAnd(And and, int t, int f) {
        int label = f != 0 ? f : newlabel();
        jumping(and.expr1, 0, label);
        jumping(and.expr2, t, f);
        if (f == 0) emitlabel(label);
    }

    private void jumpingOr(Or or, int t, int f) {
        int label = t != 0 ? t : newlabel();
        jumping(or.expr1, label, 0);
        jumping(or.expr2, t, f);
        if (t == 0) emitlabel(label);
    }

    private void jumpingRel(Rel rel, int t, int f) {
        Expr a = reduce(rel.expr1);
        Expr b = reduce(rel.expr2);

        String test = str(a) + " " + str(rel.op) + " " + str(b);
        emitjumps(test, t, f);
    }

    private void jumpingNot(Not not, int t, int f) {
        jumping(not.expr2, f, t);
    }

    private void jumpingAccess(Access access, int t, int f) {
        emitjumps(str(reduce(access)), t, f);
    }

    private void emitjumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        } else if (t != 0) emit("if " + test + " goto L" + t);
        else if (f != 0) emit("iffalse " + test + " goto L" + f);
        // nothing since both t and f fall through
    }

    private int newlabel() {
        return ++labels;
    }

    private void emitlabel(int i) {
        out.print("L" + i + ":");
    }

    private void emit(String s) {
        out.println("\t" + s);
    }

    private String str(Expr expr) {
        return switch (expr) {
            case Access access -> str(access.array) + " [ " + str(access.index) + " ]";
            case Arith arith -> str(arith.expr1) + " " + str(arith.op) + " " + str(arith.expr2);
            case Not not -> str(not.op) + " " + str(not.expr2);
            case Logical logical -> str(logical.expr1) + " " + str(logical.op) + " " + str(logical.expr2);
            case Temp temp -> "t" + temp.number;
            case Unary unary -> str(unary.op) + " " + str(unary.expr);
            default -> str(expr.op);
        };
    }

    private String str(Token op) {
        return switch (op) {
            case Array array -> "[" + array.size + "] " + str(array.of);
            case Num num -> String.valueOf(num.value);
            case Real real -> String.valueOf(real.value);
            case Word word -> word.lexeme;
            default -> String.valueOf((char) op.tag);
        };
    }
}