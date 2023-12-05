package dragonbook.inter.stmt;

import dragonbook.inter.expr.Access;
import dragonbook.inter.expr.Expr;
import dragonbook.inter.expr.Id;
import dragonbook.symbols.Array;
import dragonbook.symbols.Type;

public class SetElem extends Stmt {
    public final Id array;
    public final Expr index;
    public final Expr expr;

    public SetElem(Access access, Expr expr) {
        if (check(access.type, expr.type) == null) error("type error");
        array = access.array;
        index = access.index;
        this.expr = expr;
    }

    public Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) return null;
        else if (type1 == type2) return type2;
        else if (type1.isNumeric() && type2.isNumeric()) return type2;
        else return null;
    }

    @Override
    public String toString() {
        return array + "[" + index + "] = " + expr;
    }
}