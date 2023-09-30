package inter;

import lexer.*;
import symbols.*;

public class Id extends Expr {
    private final int offset; // relative address

    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }
}