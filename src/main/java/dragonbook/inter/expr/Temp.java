package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public record Temp(Type type, int number) implements Expr {

    @Override
    public Token op() {
        return Word.TEMP;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}