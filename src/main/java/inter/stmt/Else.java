package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class Else extends Stmt {
    public final Expr expr;
    public final Stmt stmt1;
    public final Stmt stmt2;

    public Else(Expr expr, Stmt stmt1, Stmt stmt2) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in if");
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
}