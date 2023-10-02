package inter.expr;

import lexer.*;
import symbols.*;

public class Constant extends Expr {
    public Constant(Token tok, Type type) {
        super(tok, type);
    }

    public Constant(int i) {
        super(new Num(i), Type.INT);
    }

    @Override
    public void jumping(int t, int f) {
        if (this == TRUE && t != 0) emit("goto L" + t);
        else if (this == FALSE && f != 0) emit("goto L" + f);
    }

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}