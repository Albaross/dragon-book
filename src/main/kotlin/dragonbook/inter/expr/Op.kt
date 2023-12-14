package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Op extends Expr {
    public Op(Token op, Type type) {
        super(op, type);
        if (type == null) error("type error");
    }
}