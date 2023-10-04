package inter.expr;

import inter.*;
import lexer.*;
import symbols.*;

public interface Expr extends Node {

    Token op();

    Type type();
}