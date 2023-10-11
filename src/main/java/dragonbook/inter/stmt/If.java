package dragonbook.inter.stmt;

import dragonbook.error.ParseError;
import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public record If(Expr expr, Stmt stmt) implements Stmt {

    public If {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in if");
    }
}