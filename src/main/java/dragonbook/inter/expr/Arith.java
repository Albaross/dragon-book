package dragonbook.inter.expr;

import dragonbook.error.ParseError;
import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public record Arith(Token op, Expr expr1, Expr expr2) implements Op {

    public Arith {
        if (!expr1.type().isNumeric() || !expr2.type().isNumeric()) throw new ParseError("type error");
    }

    @Override
    public Type type() {
        return max(expr1.type(), expr2.type());
    }

    private static Type max(Type type1, Type type2) {
        if (type1 == Type.FLOAT || type2 == Type.FLOAT) return Type.FLOAT;
        else if (type1 == Type.INT || type2 == Type.INT) return Type.INT;
        return Type.CHAR;
    }
}