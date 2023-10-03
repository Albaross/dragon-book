package inter.expr;

import inter.*;
import lexer.*;
import symbols.*;

public class Expr extends Node {
    public final Token op;
    public Type type;

    public Expr(Token op, Type type) {
        this.op = op;
        this.type = type;
    }

    @Override
    public String toString() {
        return op.toString();
    }
}