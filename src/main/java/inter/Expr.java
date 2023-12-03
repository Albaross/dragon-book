package inter;

import lexer.*;
import symbols.*;

public class Expr extends Node {
    public Token op;
    public Type type;

    Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public Expr gen() {
        return genExpr(this);
    }

    private static Expr genExpr(Expr expr) {
        return expr;
    }

    public Expr reduce() {
        return reduceExpr(this);
    }

    private static Expr reduceExpr(Expr expr) {
        return expr;
    }

    public void jumping(int t, int f) {
        jumpingExpr(this, t, f);
    }

    private static void jumpingExpr(Expr expr, int t, int f) {
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

    public String toString() {
        return op.toString();
    }
}