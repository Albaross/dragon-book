package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class If extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public If(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.BOOL) expr.error("boolean required in if");
    }
}