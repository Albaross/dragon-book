package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class SetElem extends Stmt {
    public final Id array;
    public final Expr index;
    public final Expr expr;

    public SetElem(Access access, Expr expr) {
        checkTypes(access.type, expr.type);
        this.array = access.array;
        this.index = access.index;
        this.expr = expr;
    }

    private void checkTypes(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) throw new ParseError("type error");
        else if (type1 == type2) return;
        else if (type1.isNumeric() && type2.isNumeric()) return;
        throw new ParseError("type error");
    }
}