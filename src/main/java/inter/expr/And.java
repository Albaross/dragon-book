package inter.expr;

import lexer.*;

public class And extends Logical {
    public And(Token tok, Expr expr1, Expr expr2) {
        super(tok, expr1, expr2);
    }
}