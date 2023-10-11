package inter;

import error.ParseError;
import symbols.Type;

public record Do(Stmt stmt, Expr expr) implements Stmt {

    public Do {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in do");
    }
}