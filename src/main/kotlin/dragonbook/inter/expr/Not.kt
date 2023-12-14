package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class Not extends Logical {
    public Not(Token op, Expr expr) {
        super(op, expr, expr);
    }

    @Override
    public String toString() {
        return op + " " + expr2;
    }
}