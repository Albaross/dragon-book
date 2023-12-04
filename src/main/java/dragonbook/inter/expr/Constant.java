package dragonbook.inter.expr;

import dragonbook.lexer.Num;
import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Constant extends Expr {
    public Constant(Token tok, Type p) {
        super(tok, p);
    }

    public Constant(int i) {
        super(new Num(i), Type.INT);
    }

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}