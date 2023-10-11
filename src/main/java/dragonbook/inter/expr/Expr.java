package dragonbook.inter.expr;

import dragonbook.lexer.Token;
import dragonbook.symbols.Type;
import dragonbook.inter.Node;

public interface Expr extends Node {

    Token op();

    Type type();
}