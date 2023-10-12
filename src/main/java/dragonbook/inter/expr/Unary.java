package dragonbook.inter.expr;

import dragonbook.error.ParseError;
import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public record Unary(Expr expr) implements Op {

    public Unary { // handles minus, for ! see Not
        if (!expr.type().isNumeric()) throw new ParseError("type error");
    }

    @Override
    public Token op() {
        return Word.MINUS;
    }

    @Override
    public Type type() {
        return expr.type() == Type.FLOAT ? Type.FLOAT : Type.INT;
    }
}