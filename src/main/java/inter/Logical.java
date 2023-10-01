package inter;

import error.ParseException;
import lexer.*;
import symbols.*;

public class Logical extends Expr {
    protected final Expr expr1, expr2;

    Logical(Token tok, Expr expr1, Expr expr2) {
        super(tok, null); // null type to start
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = check(expr1.type, expr2.type);
    }

    protected Type check(Type type1, Type type2) {
        if (type1 == Type.BOOL && type2 == Type.BOOL) return Type.BOOL;
        throw new ParseException("type error");
    }

    @Override
    public Expr gen() {
        int f = newlabel();
        int a = newlabel();
        Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp + " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(temp + " = false");
        emitlabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return expr1 + " " + op + " " + expr2;
    }
}