package inter.expr;

import lexer.*;

public record And(Expr expr1, Expr expr2) implements Logical {

    public And {
        checkTypes();
    }

    @Override
    public Token op() {
        return Token.AND;
    }

    @Override
    public String toString() {
        return expr1() + " " + op() + " " + expr2();
    }
}