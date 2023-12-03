package inter;

public class Stmt extends Node {
    public Stmt() {}

    public static Stmt Null = new Stmt();

    public void gen(int begin, int after) {
        genStmt(this, begin, after);
    } // called with labels begin and after

    private static void genStmt(Stmt stmt, int begin, int after) {}

    int after = 0; // saves label after
    public static Stmt Enclosing = Stmt.Null; // used for break stmts
}