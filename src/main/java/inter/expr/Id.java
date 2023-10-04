package inter.expr;

import lexer.*;
import symbols.*;

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