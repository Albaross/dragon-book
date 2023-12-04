package dragonbook.inter;

import dragonbook.lexer.Lexer;

public class Node {
    int lexline;

    public Node() {
        lexline = Lexer.line;
    }

    public void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }
}