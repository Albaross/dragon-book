package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public record Or(Expr expr1, Expr expr2) implements Logical {

    public Or {
        checkTypes();
    }

    @Override
    public Token op() {
        return Token.OR;
    }
}