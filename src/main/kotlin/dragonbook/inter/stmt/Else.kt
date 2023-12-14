package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class Else extends Stmt {
    public final Expr condition;
    public final Stmt then;
    public final Stmt elseStmt;

    public Else(Expr condition, Stmt then, Stmt elseStmt) {
        if (condition.type != Type.BOOL) condition.error("boolean required in if");
        this.condition = condition;
        this.then = then;
        this.elseStmt = elseStmt;
    }
}