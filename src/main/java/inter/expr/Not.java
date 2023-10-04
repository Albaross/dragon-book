package inter.expr;

import lexer.*;

public record Not(Expr expr) implements Logical {

    public Not {
        checkTypes();
    }

    @Override
    public Token op() {
        return Token.NOT;
    }

    @Override
    public Expr expr1() {
        return expr;
    }

    @Override
    public Expr expr2() {
        return expr;
    }

    @Override
    public String toString() {
        return op() + " " + expr2();
    }
}