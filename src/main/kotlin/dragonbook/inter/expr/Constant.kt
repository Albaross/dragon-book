package dragonbook.inter.expr;

import dragonbook.lexer.Num;
import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Constant extends Expr {
    public Constant(Token value, Type type) {
        super(value, type);
    }

    public Constant(int value) {
        super(new Num(value), Type.INT);
    }

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}