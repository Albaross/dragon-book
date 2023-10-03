package inter.expr;

import error.*;
import lexer.*;
import symbols.*;

public class Unary extends Op {

    public final Expr expr;

    public Unary(Token tok, Expr expr) { // handles minus, for ! see Not
        super(tok, check(expr.type));
        this.expr = expr;
    }

    @Override
    public String toString() {
        return op + " " + expr;
    }

    private static Type check(Type type) {
        if (!type.isNumeric()) throw new ParseError("type error");
        else if (type == Type.FLOAT) return Type.FLOAT;
        return Type.INT;
    }
}