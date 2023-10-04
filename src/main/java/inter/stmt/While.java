package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class While extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public void init(Expr expr, Stmt stmt) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in while");
        this.expr = expr;
        this.stmt = stmt;
    }
}