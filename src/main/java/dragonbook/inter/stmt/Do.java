package dragonbook.inter.stmt;

import dragonbook.error.ParseError;
import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public record Do(Stmt stmt, Expr expr) implements Stmt {

    public Do {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in do");
    }
}