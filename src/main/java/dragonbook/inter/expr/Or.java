package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public record Or(Token op, Expr expr1, Expr expr2) implements Logical {

    public Or {
        checkTypes();
    }
}