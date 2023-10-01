package inter;

import symbols.*;

public class SetElem extends Stmt {
    private final Id array;
    private final Expr index;
    private final Expr expr;

    public SetElem(Access access, Expr expr) {
        this.array = access.array;
        this.index = access.index;
        this.expr = expr;
        if (check(access.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return p2;
        else if (p1.isNumeric() && p2.isNumeric()) return p2;
        else return null;
    }

    @Override
    public void gen(int begin, int after) {
        final String s1 = index.reduce().toString();
        final String s2 = expr.reduce().toString();
        emit(array + "[" + s1 + "] = " + s2);
    }
}