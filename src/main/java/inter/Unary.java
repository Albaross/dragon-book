package inter;

import lexer.*;
import symbols.*;

public class Unary extends Op {
    private final Expr expr;

    public Unary(Token tok, Expr x) { // handles minus, for ! see Not
        super(tok, null);
        expr = x;
        type = max(expr.type);
    }

    public Expr gen() {
        return new Unary(op, expr.reduce());
    }

    public Type max(Type type) {
        if (!type.isNumeric()) error("type error");
        else if (type == Type.Float) return Type.Float;
        else return Type.Int;
        return null;
    }

    public String toString() {
        return op + " " + expr;
    }
}