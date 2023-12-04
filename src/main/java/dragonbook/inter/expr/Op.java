package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Op extends Expr {
    public Op(Token tok, Type p) {
        super(tok, p);
    }
}