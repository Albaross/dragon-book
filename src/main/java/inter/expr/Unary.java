package inter.expr;

import error.*;
import lexer.*;
import symbols.*;

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

    @Override
    public String toString() {
        return op() + " " + expr();
    }
}