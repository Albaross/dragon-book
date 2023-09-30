package inter;

import symbols.*;

public class Set extends Stmt {
    private final Id id;
    private final Expr expr;

    public Set(Id i, Expr x) {
        id = i;
        expr = x;
        if (check(id.type, expr.type) == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        if (p1.isNumeric() && p2.isNumeric()) return p2;
        else if (p1 == Type.Bool && p2 == Type.Bool) return p2;
        else return null;
    }

    public void gen(int b, int a) {
        emit(id + " = " + expr.gen());
    }
}