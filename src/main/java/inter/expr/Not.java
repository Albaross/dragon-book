package inter.expr;

import lexer.*;

public class Not extends Logical {
    public Not(Token tok, Expr expr) {
        super(tok, expr, expr);
    }

    @Override
    public String toString() {
        return op + " " + expr2;
    }
}