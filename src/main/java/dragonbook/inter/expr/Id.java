package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public record Id(Word id, Type type) implements Expr {

    @Override
    public Token op() {
        return id;
    }
}