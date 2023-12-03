package inter;

import symbols.*;

public class Do extends Stmt {
    Expr expr;
    Stmt stmt;

    public Do() {
        expr = null;
        stmt = null;
    }

    public void init(Stmt s, Expr x) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in do");
    }

    public void gen(int begin, int after) {
        genDo(this, begin, after);
    }

    private static void genDo(Do doLoop, int begin, int after) {
        doLoop.after = after;
        int label = newlabel(); // label for expr
        doLoop.stmt.gen(begin, label);
        emitlabel(label);
        doLoop.expr.jumping(begin, 0);
    }
}