package inter;

import lexer.*;
import symbols.*;

public class Expr extends Node {
    public Token op;
    public Type type;

    Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public Expr gen() {
        return gen.Generator.genExpr(this);
    }

    public Expr reduce() {
        return gen.Generator.reduceExpr(this);
    }

    public void jumping(int t, int f) {
        gen.Generator.jumpingExpr(this, t, f);
    }

    public String toString() {
        return op.toString();
    }
}