package dragonbook.inter.stmt;

public record Seq(Stmt head, Stmt tail) implements Stmt {}