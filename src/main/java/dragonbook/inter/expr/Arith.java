package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Arith extends Op {
    public final Expr expr1;
    public final Expr expr2;

    public Arith(Token op, Expr expr1, Expr expr2) {
        super(op, Type.max(expr1.type, expr2.type));
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}