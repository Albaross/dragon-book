package inter;

import lexer.Token;
import symbols.Type;

public interface Expr extends Node {

    Token op();

    Type type();
}