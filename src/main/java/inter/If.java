package inter;

import error.ParseError;
import symbols.Type;

public record If(Expr expr, Stmt stmt) implements Stmt {

    public If {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in if");
    }
}