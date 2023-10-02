package inter.expr;

import lexer.*;

public class And extends Logical {
    public And(Token tok, Expr expr1, Expr expr2) {
        super(tok, expr1, expr2);
    }

    @Override
    public void jumping(int t, int f) {
        final int label = f != 0 ? f : newlabel();
        expr1.jumping(0, label);
        expr2.jumping(t, f);
        if (f == 0) emitlabel(label);
    }
}