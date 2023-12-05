package dragonbook.inter.expr;

import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Id extends Expr {
    public final int offset; // relative address

    public Id(Word id, Type type, int offset) {
        super(id, type);
        this.offset = offset;
    }
}