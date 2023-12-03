package inter;

import lexer.*;
import symbols.*;

public class Unary extends Op {
    public Expr expr;

    public Unary(Token tok, Expr x) { // handles minus, for ! see Not
        super(tok, null);
        expr = x;
        type = Type.max(Type.Int, expr.type);
        if (type == null) error("type error");
    }

    public Expr gen() {
        return genUnary(this);
    }

    private static Expr genUnary(Unary unary) {
        return new Unary(unary.op, unary.expr.reduce());
    }

    public String toString() {
        return op.toString() + " " + expr.toString();
    }
}