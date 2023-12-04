package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Unary extends Op {
    public Expr expr;

    public Unary(Token tok, Expr x) { // handles minus, for ! see Not
        super(tok, null);
        expr = x;
        type = Type.max(Type.INT, expr.type);
        if (type == null) error("type error");
    }

    public String toString() {
        return op + " " + expr;
    }
}