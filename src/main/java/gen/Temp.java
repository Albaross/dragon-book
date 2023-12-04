package gen;

import inter.*;
import lexer.*;
import symbols.*;

public class Temp extends Expr {
    public int number;

    public Temp(Type p, int number) {
        super(Word.temp, p);
        this.number = number;
    }

    public String toString() {
        return "t" + number;
    }
}