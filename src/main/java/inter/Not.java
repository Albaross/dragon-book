package inter;

import lexer.*;

public class Not extends Logical {
    public Not(Token tok, Expr expr) {
        super(tok, expr, expr);
    }

    @Override
    public void jumping(int t, int f) {
        expr2.jumping(f, t);
    }

    @Override
    public String toString() {
        return op + " " + expr2;
    }
}