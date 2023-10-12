package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public record And(Token op, Expr expr1, Expr expr2) implements Logical {

    public And {
        checkTypes();
    }
}