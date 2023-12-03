package inter;

import lexer.*;

public class Or extends Logical {
    public Or(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public void jumping(int t, int f) {
        jumpingOr(this, t, f);
    }

    private static void jumpingOr(Or or, int t, int f) {
        int label = t != 0 ? t : newlabel();
        or.expr1.jumping(label, 0);
        or.expr2.jumping(t, f);
        if (t == 0) emitlabel(label);
    }
}