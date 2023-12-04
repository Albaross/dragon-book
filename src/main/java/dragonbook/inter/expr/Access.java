package dragonbook.inter.expr;

import dragonbook.lexer.Tag;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Access extends Op {
    public Id array;
    public Expr index;

    public Access(Id a, Expr i, Type p) { // p is element type after
        super(new Word("[]", Tag.INDEX), p); // flattening the array
        array = a;
        index = i;
    }

    public String toString() {
        return array + "[" + index + "]";
    }
}