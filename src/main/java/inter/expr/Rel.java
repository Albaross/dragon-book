package inter.expr;

import lexer.*;
import symbols.*;

public class Rel extends Logical {
    public Rel(Token tok, Expr expr1, Expr expr2) {
        super(tok, expr1, expr2);
    }

    @Override
    protected Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) return null;
        else if (type1 == type2) return Type.BOOL;
        else return null;
    }
}