package inter.expr;

import lexer.*;
import symbols.*;

public class Id extends Expr {
    private final int offset; // relative address

    public Id(Word id, Type type, int offset) {
        super(id, type);
        this.offset = offset;
    }
}