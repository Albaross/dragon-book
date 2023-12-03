package inter;

import lexer.*;
import symbols.*;

public class Constant extends Expr {
    public Constant(Token tok, Type p) {
        super(tok, p);
    }

    public Constant(int i) {
        super(new Num(i), Type.Int);
    }

    public static final Constant
            True = new Constant(Word.True, Type.Bool),
            False = new Constant(Word.False, Type.Bool);

    public void jumping(int t, int f) {
        jumpingConstant(this, t, f);
    }

    private static void jumpingConstant(Constant constant, int t, int f) {
        if (constant == True && t != 0) emit("goto L" + t);
        else if (constant == False && f != 0) emit("goto L" + f);
    }
}