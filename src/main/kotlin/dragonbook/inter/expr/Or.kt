package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class Or extends Logical {
    public Or(Token op, Expr expr1, Expr expr2) {
        super(op, expr1, expr2);
    }
}