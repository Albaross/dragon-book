package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Array;
import dragonbook.symbols.Type;

public class Rel extends Logical {
    public Rel(Token op, Expr expr1, Expr expr2) {
        super(op, expr1, expr2, check(expr1.type, expr2.type));
    }

    private static Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) return null;
        else if (type1 == type2) return Type.BOOL;
        else return null;
    }
}