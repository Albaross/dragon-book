package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class Or extends Logical {
    public Or(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }
}