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

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}