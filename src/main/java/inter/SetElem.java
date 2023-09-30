package inter;

import symbols.*;

public class SetElem extends Stmt {
    private final Id array;
    private final Expr index;
    private final Expr expr;

    public SetElem(Access x, Expr y) {
        array = x.array;
        index = x.index;
        expr = y;
        if (check(x.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return p2;
        else if (p1.isNumeric() && p2.isNumeric()) return p2;
        else return null;
    }

    public void gen(int b, int a) {
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();
        emit(array + "[" + s1 + "] = " + s2);
    }
}