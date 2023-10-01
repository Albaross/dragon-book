package inter; // File Node.java

import error.*;
import lexer.*;

import java.io.PrintStream;

public class Node {
    public static PrintStream out = System.out;
    int lexline = 0;

    Node() {
        lexline = Lexer.line;
    }

    void error(String s) {
        throw new ParseError("near line " + lexline + ": " + s);
    }

    static int labels = 0;

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