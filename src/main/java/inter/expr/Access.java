package inter.expr;

import lexer.*;
import symbols.*;

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