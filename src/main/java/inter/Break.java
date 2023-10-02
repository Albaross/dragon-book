package inter;

import error.*;

public class Break extends Stmt {
    private final Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.Null) throw new ParseError("unenclosed break");
        this.stmt = Stmt.Enclosing;
    }

    @Override
    public void gen(int begin, int after) {
        emit("goto L" + stmt.after);
    }
}