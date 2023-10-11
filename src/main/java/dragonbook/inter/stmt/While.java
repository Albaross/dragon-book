package dragonbook.inter.stmt;

import dragonbook.error.ParseError;
import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public record While(Expr expr, Stmt stmt) implements Stmt {

    public While {
        if (expr.type() != Type.BOOL) throw new ParseError("boolean required in while");
    }
}