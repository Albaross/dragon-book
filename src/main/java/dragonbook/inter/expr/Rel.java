package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Array;
import dragonbook.symbols.Type;

public class Rel extends Logical {
    public Rel(Token tok, Expr x1, Expr x2) {
        super(tok, x1, x2);
    }

    public Type check(Type p1, Type p2) {
        if (p1 instanceof Array || p2 instanceof Array) return null;
        else if (p1 == p2) return Type.BOOL;
        else return null;
    }
}