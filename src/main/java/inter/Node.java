package inter;

import lexer.*;

public class Node {
    int lexline;

    Node() {
        lexline = Lexer.line;
    }

    void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }
}