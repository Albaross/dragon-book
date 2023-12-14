package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Unary extends Op {
    public final Expr expr;

    public Unary(Token op, Expr expr) { // handles minus, for ! see Not
        super(op, max(Type.INT, expr.type));
        this.expr = expr;
    }

    private static Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) return null;
        else if (type1 == Type.FLOAT || type2 == Type.FLOAT) return Type.FLOAT;
        else if (type1 == Type.INT || type2 == Type.INT) return Type.INT;
        else return Type.CHAR;
    }

    @Override
    public String toString() {
        return op + " " + expr;
    }
}