package inter.stmt;

import inter.*;

public class Stmt implements Node {

    public static final Stmt NULL = new Stmt();

    public int after = 0; // saves label after
    public static Stmt Enclosing = Stmt.NULL; // used for break stmts
}