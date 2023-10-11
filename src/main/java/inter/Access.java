package inter;

import lexer.Token;
import lexer.Word;
import symbols.Type;

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