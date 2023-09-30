package inter;

import lexer.*;
import symbols.*;

public class Unary extends Op {

    private final Expr expr;

    public Unary(Token tok, Expr expr) { // handles minus, for ! see Not
        super(tok, max(expr.type));
        this.expr = expr;
    }

    public Expr gen() {
        return new Unary(op, expr.reduce());
    }

    public static Type max(Type type) {
        if (!type.isNumeric()) error("type error");
        else if (type == Type.Float) return Type.Float;
        return Type.Int;
    }

    public String toString() {
        return op + " " + expr;
    }
}