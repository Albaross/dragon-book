package inter.stmt;

import error.*;

public class Break extends Stmt {
    public final Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.NULL) throw new ParseError("unenclosed break");
        this.stmt = Stmt.Enclosing;
    }
}