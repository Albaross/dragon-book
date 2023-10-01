package inter;

import error.ParseException;
import symbols.*;

public class SetElem extends Stmt {
    private final Id array;
    private final Expr index;
    private final Expr expr;

    public SetElem(Access access, Expr expr) {
        checkTypes(access.type, expr.type);
        this.array = access.array;
        this.index = access.index;
        this.expr = expr;
    }

    private void checkTypes(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) throw new ParseException("type error");
        else if (type1 == type2) return;
        else if (type1.isNumeric() && type2.isNumeric()) return;
        throw new ParseException("type error");
    }

    @Override
    public void gen(int begin, int after) {
        final String s1 = index.reduce().toString();
        final String s2 = expr.reduce().toString();
        emit(array + "[" + s1 + "] = " + s2);
    }
}