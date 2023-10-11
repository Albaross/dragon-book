package inter;

import lexer.Token;
import lexer.Word;
import symbols.Type;

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