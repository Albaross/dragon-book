package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class And extends Logical {
    public And(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }
}