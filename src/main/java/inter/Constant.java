package inter;

import lexer.Num;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public record Constant(Token value, Type type) implements Expr {

    public Constant(int i) {
        this(new Num(i), Type.INT);
    }

    @Override
    public Token op() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL);
    public static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL);
}