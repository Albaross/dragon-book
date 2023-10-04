package inter.expr;

import lexer.*;
import symbols.*;

public class Temp extends Expr {
    private static int count = 0;
    private final int number;

    public Temp(Type p) {
        super(Word.TEMP, p);
        this.number = ++count;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}