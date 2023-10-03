package inter.expr;

import lexer.*;

public class Or extends Logical {
    public Or(Token tok, Expr expr1, Expr expr2) {
        super(tok, expr1, expr2);
    }
}