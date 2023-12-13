package dragonbook.gen;

import dragonbook.inter.expr.Expr;
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

public class Temp extends Expr {
    public int number;

    public Temp(Type p, int number) {
        super(Word.TEMP, p);
        this.number = number;
    }

    public String toString() {
        return "t" + number;
    }
}