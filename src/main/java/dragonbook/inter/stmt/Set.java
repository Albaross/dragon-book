package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.inter.expr.Id;
import dragonbook.symbols.Type;

public class Set extends Stmt {
    public final Id id;
    public final Expr expr;

    public Set(Id id, Expr expr) {
        if (check(id.type, expr.type) == null) error("type error");
        this.id = id;
        this.expr = expr;
    }

    private Type check(Type type1, Type type2) {
        if (type1.isNumeric() && type2.isNumeric()) return type2;
        else if (type1 == Type.BOOL && type2 == Type.BOOL) return type2;
        else return null;
    }

    @Override
    public String toString() {
        return id + " = " + expr;
    }
}