package inter.expr;

import lexer.*;

public record Or(Expr expr1, Expr expr2) implements Logical {

    public Or {
        checkTypes();
    }

    @Override
    public Token op() {
        return Token.OR;
    }

    @Override
    public String toString() {
        return expr1() + " " + op() + " " + expr2();
    }
}