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
        return genAccess(this);
    }

    private static Expr genAccess(Access access) {
        return new Access(access.array, access.index.reduce(), access.type);
    }

    public void jumping(int t, int f) {
        jumpingAccess(this, t, f);
    }

    private static void jumpingAccess(Access access, int t, int f) {
        emitjumps(access.reduce().toString(), t, f);
    }


    public String toString() {
        return array.toString() + " [ " + index.toString() + " ]";
    }
}