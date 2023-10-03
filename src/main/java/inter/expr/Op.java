package inter.expr;

import lexer.*;
import symbols.*;

public class Op extends Expr {
    public Op(Token tok, Type type) {
        super(tok, type);
    }
}