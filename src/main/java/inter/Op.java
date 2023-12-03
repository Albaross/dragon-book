package inter;

import lexer.*;
import symbols.*;

public class Op extends Expr {
    public Op(Token tok, Type p) {
        super(tok, p);
    }

    public Expr reduce() {
        return reduceOp(this);
    }

    private static Expr reduceOp(Op op) {
        Expr x = op.gen();
        Temp t = new Temp(op.type);
        emit(t.toString() + " = " + x.toString());
        return t;
    }
}