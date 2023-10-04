package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public record Do(Stmt stmt, Expr expr) implements Stmt {

    public Do {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in do");
    }
}