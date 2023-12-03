package inter;

public class Break extends Stmt {
    public Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.Null) error("unenclosed break");
        stmt = Stmt.Enclosing;
    }

    public void gen(int begin, int after) {
        gen.Generator.genBreak(this, begin, after);
    }
}