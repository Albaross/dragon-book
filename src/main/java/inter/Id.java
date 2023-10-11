package inter;

import lexer.Token;
import lexer.Word;
import symbols.Type;

public record Id(Word id, Type type) implements Expr {

    @Override
    public Token op() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}