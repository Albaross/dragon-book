package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Logical extends Expr {
    public Expr expr1, expr2;

    Logical(Token tok, Expr x1, Expr x2) {
        super(tok, null); // null type to start
        expr1 = x1;
        expr2 = x2;
        type = check(expr1.type, expr2.type);
        if (type == null) error("type error");
    }

    public Type check(Type p1, Type p2) {
        if (p1 == Type.BOOL && p2 == Type.BOOL) return Type.BOOL;
        else return null;
    }

    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}