package inter;

import lexer.Lexer;

import java.io.PrintStream;

public class Node {

    public static PrintStream out = System.out;
    private final int lexline;

    public Node() {
        lexline = Lexer.line;
    }

    void error(String s) {
        throw new RuntimeException("near line " + lexline + ": " + s);
    }

    private static int labels = 0;

    public int newlabel() {
        return ++labels;
    }

    public void emitlabel(int i) {
        out.print("L" + i + ":");
    }

    public void emit(String s) {
        out.println("\t" + s);
    }
}