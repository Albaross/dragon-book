package dragonbook.inter.stmt;

import dragonbook.inter.expr.Expr;
import dragonbook.symbols.Type;

public class While extends Stmt {
    public Expr condition;
    public Stmt stmt;

    public While(Expr condition, Stmt stmt) {
        if (condition.type != Type.BOOL) condition.error("boolean required in while");
        this.condition = condition;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "while (" + condition + ") " + stmt;
    }
}