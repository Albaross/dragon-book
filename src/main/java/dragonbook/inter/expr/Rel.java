package dragonbook.inter.expr;

import dragonbook.error.ParseError;
import dragonbook.lexer.Token;
import dragonbook.symbols.Array;

public record Rel(Token op, Expr expr1, Expr expr2) implements Logical {

    public Rel {
        if (expr1.type() instanceof Array || expr2.type() instanceof Array || expr1.type() != expr2.type())
            throw new ParseError("type error");
    }
}