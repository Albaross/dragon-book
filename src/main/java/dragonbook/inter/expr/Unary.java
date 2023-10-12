package dragonbook.inter.expr;

import dragonbook.error.ParseError;
import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public record Unary(Token op, Expr expr) implements Op {

    public Unary { // handles minus, for ! see Not
        if (!expr.type().isNumeric()) throw new ParseError("type error");
    }

    @Override
    public Type type() {
        return expr.type() == Type.FLOAT ? Type.FLOAT : Type.INT;
    }
}