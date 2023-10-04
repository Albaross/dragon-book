package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public record SetElem(Access access, Expr expr) implements Stmt {

    public SetElem {
        checkTypes(access.type, expr.type);
    }

    private void checkTypes(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) throw new ParseError("type error");
        else if (type1 == type2) return;
        else if (type1.isNumeric() && type2.isNumeric()) return;
        throw new ParseError("type error");
    }

    public Id array() {
        return access.array;
    }

    public Expr index() {
        return access.index;
    }
}