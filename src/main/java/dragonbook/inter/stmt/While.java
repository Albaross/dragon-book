package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class While extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public While() {
        expr = null;
        stmt = null;
    }

    public void init(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.BOOL) expr.error("boolean required in while");
    }
}