package dragonbook.inter.expr;

import dragonbook.inter.Node;
import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

public class Expr extends Node {
    public final Token op;
    public final Type type;

    public Expr(Token op, Type type) {
        this.op = op;
        this.type = type;
    }

    @Override
    public String toString() {
        return op.toString();
    }
}