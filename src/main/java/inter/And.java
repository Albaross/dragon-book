package inter;

import lexer.*;

public class And extends Logical {
    public And(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public void jumping(int t, int f) {
        jumpingAnd(this, t, f);
    }

    private static void jumpingAnd(And and, int t, int f) {
        int label = f != 0 ? f : newlabel();
        and.expr1.jumping(0, label);
        and.expr2.jumping(t, f);
        if (f == 0) emitlabel(label);
    }
}