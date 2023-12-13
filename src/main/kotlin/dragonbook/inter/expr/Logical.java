package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Logical extends Expr {
    public final Expr expr1;
    public final Expr expr2;

    public Logical(Token op, Expr expr1, Expr expr2) {
        this(op, expr1, expr2, check(expr1.type, expr2.type));
    }

    public Logical(Token op, Expr expr1, Expr expr2, Type type) {
        super(op, type);
        if (type == null) error("type error");
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    private static Type check(Type type1, Type type2) {
        if (type1 == Type.BOOL && type2 == Type.BOOL) return Type.BOOL;
        else return null;
    }

    @Override
    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}