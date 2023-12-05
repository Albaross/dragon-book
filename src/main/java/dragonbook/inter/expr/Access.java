package dragonbook.inter.expr;

import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Access extends Op {
    public final Id array;
    public final Expr index;

    public Access(Id array, Expr index, Type type) { // type is element type after
        super(Word.INDEX, type); // flattening the array
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return array + "[" + index + "]";
    }
}