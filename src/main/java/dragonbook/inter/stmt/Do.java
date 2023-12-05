package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class Do extends Stmt {
    public final Expr condition;
    public final Stmt stmt;

    public Do(Stmt stmt, Expr condition) {
        if (condition.type != Type.BOOL) condition.error("boolean required in do");
        this.condition = condition;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "do " + stmt + " while (" + condition + ")";
    }
}