package inter.expr;

import error.*;
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

    @Override
    public Expr gen() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }

    @Override
    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }

    private static Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) throw new ParseError("type error");
        else if (type1 == Type.FLOAT || type2 == Type.FLOAT) return Type.FLOAT;
        else if (type1 == Type.INT || type2 == Type.INT) return Type.INT;
        return Type.CHAR;
    }
}