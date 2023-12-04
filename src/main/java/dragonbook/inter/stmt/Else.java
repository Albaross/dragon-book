package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class Else extends Stmt {
    public Expr expr;
    public Stmt stmt1, stmt2;

    public Else(Expr x, Stmt s1, Stmt s2) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;
        if (expr.type != Type.BOOL) expr.error("boolean required in if");
    }
}