package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class Not extends Logical {
    public Not(Token tok, Expr x2) {
        super(tok, x2, x2);
    }

    public String toString() {
        return op + " " + expr2;
    }
}