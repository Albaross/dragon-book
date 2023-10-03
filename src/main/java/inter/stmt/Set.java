package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class Set extends Stmt {
    public final Id id;
    public final Expr expr;

    public Set(Id id, Expr expr) {
        checkTypes(id.type, expr.type);
        this.id = id;
        this.expr = expr;
    }

    private void checkTypes(Type type1, Type type2) {
        if (type1.isNumeric() && type2.isNumeric()) return;
        else if (type1 == Type.BOOL && type2 == Type.BOOL) return;
        throw new ParseError("type error");
    }
}