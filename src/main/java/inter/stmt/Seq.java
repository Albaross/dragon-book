package inter.stmt;

public class Seq extends Stmt {
    private final Stmt stmt1;
    private final Stmt stmt2;

    public Seq(Stmt stmt1, Stmt stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public void gen(int begin, int after) {
        if (stmt1 == Stmt.Null) stmt2.gen(begin, after);
        else if (stmt2 == Stmt.Null) stmt1.gen(begin, after);
        else {
            final int label = newlabel();
            stmt1.gen(begin, label);
            emitlabel(label);
            stmt2.gen(label, after);
        }
    }
}