package inter;

import lexer.*;
import symbols.*;

public class Temp extends Expr {
    private static int count = 0;
    private final int number;

    public Temp(Type p) {
        super(Word.temp, p);
        number = ++count;
    }

    public String toString() {
        return "t" + number;
    }
}