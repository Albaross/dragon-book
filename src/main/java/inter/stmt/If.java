package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public record If(Expr expr, Stmt stmt) implements Stmt {

    public If {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in if");
    }
}