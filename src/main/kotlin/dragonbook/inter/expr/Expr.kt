package dragonbook.inter.expr;

import dragonbook.inter.Node;
import dragonbook.lexer.Token;
import dragonbook.symbols.Type;

interface Expr : Node {
    val op: Token
    val type: Type
}