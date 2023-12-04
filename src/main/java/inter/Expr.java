package inter;

import lexer.*;
import symbols.*;

public class Expr extends Node {
    public Token op;
    public Type type;

    public Expr(Token tok, Type p) {
        op = tok;
        type = p;
    }

    public String toString() {
        return op.toString();
    }
}