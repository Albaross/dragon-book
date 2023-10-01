package inter;

import symbols.*;

public class Set extends Stmt {
    private final Id id;
    private final Expr expr;

    public Set(Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
        if (check(id.type, expr.type) == null) error("type error");
    }

    public Type check(Type type1, Type type2) {
        if (type1.isNumeric() && type2.isNumeric()) return type2;
        else if (type1 == Type.BOOL && type2 == Type.BOOL) return type2;
        else return null;
    }

    @Override
    public void gen(int begin, int after) {
        emit(id + " = " + expr.gen());
    }
}