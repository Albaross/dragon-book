package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class Do extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public void init(Stmt stmt, Expr expr) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in do");
        this.expr = expr;
        this.stmt = stmt;
    }
}