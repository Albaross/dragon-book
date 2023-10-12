package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public record Not(Token op, Expr expr) implements Logical {

    public Not {
        checkTypes();
    }

    @Override
    public Expr expr1() {
        return expr;
    }

    @Override
    public Expr expr2() {
        return expr;
    }
}