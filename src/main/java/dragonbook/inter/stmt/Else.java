package dragonbook.inter.stmt;

import dragonbook.error.ParseError;
import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public record Else(Expr expr, Stmt stmt1, Stmt stmt2) implements Stmt {

    public Else {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in if");
    }
}