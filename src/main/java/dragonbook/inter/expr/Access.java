package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public record Access(Id array, Expr index, Type type) implements Op {

    @Override
    public Token op() {
        return Word.ACCESS;
    }

    @Override
    public String toString() {
        return array + " [ " + index + " ]";
    }
}