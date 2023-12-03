package inter;

import symbols.*;

public class If extends Stmt {
    public Expr expr;
    public Stmt stmt;

    public If(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) expr.error("boolean required in if");
    }

    public void gen(int begin, int after) {
        gen.Generator.genIf(this, begin, after);
    }
}