package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Unary extends Op {
    public final Expr expr;

    public Unary(Token op, Expr expr) { // handles minus, for ! see Not
        super(op, Type.max(Type.INT, expr.type));
        this.expr = expr;
    }

    @Override
    public String toString() {
        return op + " " + expr;
    }
}