package inter;

import lexer.*;
import symbols.*;

public class Arith extends Op {

    private final Expr expr1;
    private final Expr expr2;

    public Arith(Token tok, Expr expr1, Expr expr2) {
        super(tok, max(expr1.type, expr2.type));
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Expr gen() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    private static Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) error("type error");
        else if (type1 == Type.Float || type2 == Type.Float) return Type.Float;
        else if (type1 == Type.Int || type2 == Type.Int) return Type.Int;
        return Type.Char;
    }

    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}