package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public record And(Expr expr1, Expr expr2) implements Logical {

    public And {
        checkTypes();
    }

    @Override
    public Token op() {
        return Token.AND;
    }
}