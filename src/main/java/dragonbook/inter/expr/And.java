package dragonbook.inter.expr;

import dragonbook.lexer.Token;

public class And extends Logical {
    public And(Token op, Expr expr1, Expr expr2) {
        super(op, expr1, expr2);
    }
}