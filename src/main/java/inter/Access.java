package inter;

import lexer.*;
import symbols.*;

public class Access extends Op {
    public Id array;
    public Expr index;

    public Access(Id a, Expr i, Type p) { // p is element type after
        super(new Word("[]", Tag.INDEX), p); // flattening the array
        array = a;
        index = i;
    }

    public Expr gen() {
        return gen.Generator.genAccess(this);
    }

    public void jumping(int t, int f) {
        gen.Generator.jumpingAccess(this, t, f);
    }


    public String toString() {
        return array.toString() + " [ " + index.toString() + " ]";
    }
}