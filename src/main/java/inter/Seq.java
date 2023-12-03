package inter;

public class Seq extends Stmt {
    Stmt stmt1;
    Stmt stmt2;

    public Seq(Stmt s1, Stmt s2) {
        stmt1 = s1;
        stmt2 = s2;
    }

    public void gen(int begin, int after) {
        genSeq(this, begin, after);
    }

    private static void genSeq(Seq seq, int begin, int after) {
        if (seq.stmt1 == Stmt.Null) seq.stmt2.gen(begin, after);
        else if (seq.stmt2 == Stmt.Null) seq.stmt1.gen(begin, after);
        else {
            int label = newlabel();
            seq.stmt1.gen(begin, label);
            emitlabel(label);
            seq.stmt2.gen(label, after);
        }
    }
}