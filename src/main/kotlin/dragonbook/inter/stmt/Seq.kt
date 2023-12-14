package dragonbook.inter.stmt;

public class Seq extends Stmt {
    public final Stmt head;
    public final Stmt tail;

    public Seq(Stmt head, Stmt stmt2) {
        this.head = head;
        this.tail = stmt2;
    }

    @Override
    public String toString() {
        return "{ " + head + "; " + tail + " }";
    }
}