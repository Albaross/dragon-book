package inter;

import symbols.*;

public class Else extends Stmt {
    Expr expr;
    Stmt stmt1, stmt2;

    public Else(Expr x, Stmt s1, Stmt s2) {
        expr = x;
        stmt1 = s1;
        stmt2 = s2;
        if (expr.type != Type.Bool) expr.error("boolean required in if");
    }

    public void gen(int begin, int after) {
        genElse(this, begin, after);
    }

    private static void genElse(Else elseStmt, int begin, int after) {
        int label1 = newlabel(); // label1 for stmt1
        int label2 = newlabel(); // label2 for stmt2
        elseStmt.expr.jumping(0, label2); // fall through to stmt1 on true
        emitlabel(label1);
        elseStmt.stmt1.gen(label1, after);
        emit("goto L" + after);
        emitlabel(label2);
        elseStmt.stmt2.gen(label2, after);
    }
}