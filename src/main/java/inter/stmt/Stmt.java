package inter.stmt;

import inter.Node;

public class Stmt extends Node {

    public static Stmt Null = new Stmt();

    public int after = 0; // saves label after
    public static Stmt Enclosing = Stmt.Null; // used for break stmts
}