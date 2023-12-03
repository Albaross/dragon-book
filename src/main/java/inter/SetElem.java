package inter;

import symbols.*;

public class SetElem extends Stmt {
    public Id array;
    public Expr index;
    public Expr expr;

    public SetElem(Access x, Expr y) {
        array = x.array;
        index = x.index;
        expr = y;
        if (check(x.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return p2;
        else if (Type.numeric(p1) && Type.numeric(p2)) return p2;
        else return null;
    }

    public void gen(int begin, int after) {
        genSetElem(this, begin, after);
    }

    private static void genSetElem(SetElem setElem, int begin, int after) {
        String s1 = setElem.index.reduce().toString();
        String s2 = setElem.expr.reduce().toString();
        emit(setElem.array.toString() + " [ " + s1 + " ] = " + s2);
    }
}