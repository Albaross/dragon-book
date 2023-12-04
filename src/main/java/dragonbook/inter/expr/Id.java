package dragonbook.inter.expr;

import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Id extends Expr {
    public int offset; // relative address

    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }
}