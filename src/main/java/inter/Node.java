package inter;

import java.io.PrintStream;

public class Node {

    public static PrintStream out = System.out;
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

    public void gen(int begin, int after) {} // called with labels begin and after
}