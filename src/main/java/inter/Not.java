package inter;

import lexer.*;

public class Not extends Logical {
    public Not(Token tok, Expr x2) {
        super(tok, x2, x2);
    }

    public void jumping(int t, int f) {
        gen.Generator.jumpingNot(this, t, f);
    }

    public String toString() {
        return op.toString() + " " + expr2.toString();
    }
}