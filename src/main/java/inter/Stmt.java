package inter;

public class Stmt extends Node {

    public static Stmt Null = new Stmt();

    int after = 0; // saves label after
    public static Stmt Enclosing = Stmt.Null; // used for break stmts
}