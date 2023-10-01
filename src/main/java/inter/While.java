package inter;

import error.ParseException;
import symbols.*;

public class While extends Stmt {
    private Expr expr;
    private Stmt stmt;

    public void init(Expr expr, Stmt stmt) {
        if (expr.type != Type.BOOL) throw new ParseException("boolean required in while");
        this.expr = expr;
        this.stmt = stmt;
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