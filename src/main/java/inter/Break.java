package inter;

public class Break extends Stmt {
    Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.Null) error("unenclosed break");
        stmt = Stmt.Enclosing;
    }

    public void gen(int begin, int after) {
        genBreak(this, begin, after);
    }

    private static void genBreak(Break breakStmt, int begin, int after) {
        emit("goto L" + breakStmt.stmt.after);
    }
}