package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class Do extends Stmt {
    private Expr expr;
    private Stmt stmt;

    public void init(Stmt stmt, Expr expr) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in do");
        this.expr = expr;
        this.stmt = stmt;
    }

    @Override
    public void gen(int begin, int after) {
        this.after = after;
        final int label = newlabel(); // label for expr
        stmt.gen(begin, label);
        emitlabel(label);
        expr.jumping(begin, 0);
    }
}