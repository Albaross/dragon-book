package inter.expr;

import lexer.*;
import symbols.*;

public class Access extends Op {
    public final Id array;
    public final Expr index;

    public Access(Id array, Expr index, Type type) { // p is element type after
        super(new Word("[]", Tag.INDEX), type); // flattening the array
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return array + " [ " + index + " ]";
    }
}