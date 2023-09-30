package inter;

import lexer.*;
import symbols.*;

public class Arith extends Op {
    private final Expr expr1, expr2;

    public Arith(Token tok, Expr x1, Expr x2) {
        super(tok, null);
        expr1 = x1;
        expr2 = x2;
        type = max(expr1.type, expr2.type);
    }

    public Expr gen() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    private Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) error("type error");
        else if (type1 == Type.Float || type2 == Type.Float) return Type.Float;
        else if (type1 == Type.Int || type2 == Type.Int) return Type.Int;
        else return Type.Char;
        return null;
    }

    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}