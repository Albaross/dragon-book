package inter;

import symbols.*;

public class Do extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public Do() {
        expr = null;
        stmt = null;
    }

    public void init(Stmt s, Expr x) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in do");
    }
}