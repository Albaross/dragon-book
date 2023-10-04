package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public record While(Expr expr, Stmt stmt) implements Stmt {

    public While {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in while");
    }
}