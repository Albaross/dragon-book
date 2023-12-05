package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class If extends Stmt {
    public final Expr condition;
    public final Stmt then;

    public If(Expr condition, Stmt then) {
        if (condition.type != Type.BOOL) condition.error("boolean required in if");
        this.condition = condition;
        this.then = then;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") " + then;
    }
}