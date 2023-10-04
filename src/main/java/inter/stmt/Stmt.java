package inter.stmt;

import inter.*;

public class Stmt implements Node {

    public static Stmt Null = new Stmt();

    public int after = 0; // saves label after
    public static Stmt Enclosing = Stmt.Null; // used for break stmts
}