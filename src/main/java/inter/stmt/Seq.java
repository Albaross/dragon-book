package inter.stmt;

public class Seq extends Stmt {
    public final Stmt stmt1;
    public final Stmt stmt2;

    public Seq(Stmt stmt1, Stmt stmt2) {
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
}