package inter;

import symbols.*;

public class While extends Stmt {
    private Expr expr;
    private Stmt stmt;

    public void init(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (this.expr.type != Type.BOOL) error("boolean required in while");
    }

    @Override
    public void gen(int begin, int after) {
        this.after = after; // save label a
        expr.jumping(0, after);
        int label = newlabel(); // label for stmt
        emitlabel(label);
        stmt.gen(label, begin);
        emit("goto L" + begin);
    }
}