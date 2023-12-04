package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

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
        if (expr.type != Type.BOOL) expr.error("boolean required in do");
    }
}