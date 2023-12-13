package dragonbook.inter;

public class Node {
    private final int lexline;

    public Node() {
        lexline = 0;
    }

    public void error(String s) {
        throw new Error("near line " + lexline + ": " + s);
    }
}