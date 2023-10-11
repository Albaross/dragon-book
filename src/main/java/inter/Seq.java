package inter;

public record Seq(Stmt head, Stmt tail) implements Stmt {}