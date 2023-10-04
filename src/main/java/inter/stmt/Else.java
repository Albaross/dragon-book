package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public record Else(Expr expr, Stmt stmt1, Stmt stmt2) implements Stmt {

    public Else {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in if");
    }
}