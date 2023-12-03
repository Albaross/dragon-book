package inter;

import symbols.*;

public class If extends Stmt {
    Expr expr;
    Stmt stmt;

    public If(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in if");
    }

    public void gen(int begin, int after) {
        genIf(this, begin, after);
    }

    private static void genIf(If ifStmt, int begin, int after) {
        int label = newlabel(); // label for the code for stmt
        ifStmt.expr.jumping(0, after); // fall through on true, goto a on false
        emitlabel(label);
        ifStmt.stmt.gen(label, after);
    }
}