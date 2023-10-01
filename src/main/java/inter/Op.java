package inter;

import lexer.*;
import symbols.*;

public class Op extends Expr {
    public Op(Token tok, Type type) {
        super(tok, type);
    }

    @Override
    public Expr reduce() {
        final Expr expr = gen();
        final Temp temp = new Temp(type);
        emit(temp + " = " + expr);
        return temp;
    }
}