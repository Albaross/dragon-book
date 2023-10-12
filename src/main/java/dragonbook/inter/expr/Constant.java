package dragonbook.inter.expr;

import dragonbook.lexer.Num;
import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public record Constant(Token value, Type type) implements Expr {

    public Constant(int i) {
        this(new Num(i), Type.INT);
    }

    @Override
    public Token op() {
        return value;
    }

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}