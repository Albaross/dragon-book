package inter.stmt;

import error.*;
import inter.expr.*;
import symbols.*;

public class Else extends Stmt {
    private final Expr expr;
    private final Stmt stmt1;
    private final Stmt stmt2;

    public Else(Expr expr, Stmt stmt1, Stmt stmt2) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in if");
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public void gen(int begin, int after) {
        final int label1 = newlabel(); // label1 for stmt1
        final int label2 = newlabel(); // label2 for stmt2
        expr.jumping(0, label2); // fall through to stmt1 on true
        emitlabel(label1);
        stmt1.gen(label1, after);
        emit("goto L" + after);
        emitlabel(label2);
        stmt2.gen(label2, after);
    }
}