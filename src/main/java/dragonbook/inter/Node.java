package dragonbook.inter;

import dragonbook.lexer.Lexer;

public class Node {
    private final int lexline;

    public Node() {
        lexline = Lexer.lineNumber;
    }

    public void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }
}