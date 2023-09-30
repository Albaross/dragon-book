package inter;

public class Break extends Stmt {
    private final Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.Null) error("unenclosed break");
        stmt = Stmt.Enclosing;
    }

    public void gen(int b, int a) {
        emit("goto L" + stmt.after);
    }
}