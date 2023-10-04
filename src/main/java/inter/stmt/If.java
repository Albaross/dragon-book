package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class If extends Stmt {
    public final Expr expr;
    public final Stmt stmt;

    public If(Expr expr, Stmt stmt) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in if");
        this.expr = expr;
        this.stmt = stmt;
    }
}