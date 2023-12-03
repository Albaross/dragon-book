package inter;

import symbols.*;

public class While extends Stmt {
    Expr expr;
    Stmt stmt;

    public While() {
        expr = null;
        stmt = null;
    }

    public void init(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in while");
    }

    public void gen(int begin, int after) {
        genWhile(this, begin, after);
    }

    private static void genWhile(While whileLoop, int begin, int after) {
        whileLoop.after = after; // save label a
        whileLoop.expr.jumping(0, after);
        int label = newlabel(); // label for stmt
        emitlabel(label);
        whileLoop.stmt.gen(label, begin);
        emit("goto L" + begin);
    }
}