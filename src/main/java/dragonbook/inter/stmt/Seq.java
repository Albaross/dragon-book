package dragonbook.inter.stmt;

public class Seq extends Stmt {
    public Stmt stmt1;
    public Stmt stmt2;

    public Seq(Stmt s1, Stmt s2) {
        stmt1 = s1;
        stmt2 = s2;
    }
}