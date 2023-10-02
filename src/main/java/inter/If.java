package inter;

import error.*;
import symbols.*;

public class If extends Stmt {
    private final Expr expr;
    private final Stmt stmt;

    public If(Expr expr, Stmt stmt) {
        if (expr.type != Type.BOOL) throw new ParseError("boolean required in if");
        this.expr = expr;
        this.stmt = stmt;
    }

    @Override
    public void gen(int begin, int after) {
        final int label = newlabel(); // label for the code for stmt
        expr.jumping(0, after); // fall through on true, goto a on false
        emitlabel(label);
        stmt.gen(label, after);
    }
}