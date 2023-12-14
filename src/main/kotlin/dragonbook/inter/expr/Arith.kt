package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Arith extends Op {
    public final Expr expr1;
    public final Expr expr2;

    public Arith(Token op, Expr expr1, Expr expr2) {
        super(op, max(expr1.type, expr2.type));
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    private static Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) return null;
        else if (type1 == Type.FLOAT || type2 == Type.FLOAT) return Type.FLOAT;
        else if (type1 == Type.INT || type2 == Type.INT) return Type.INT;
        else return Type.CHAR;
    }

    @Override
    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}