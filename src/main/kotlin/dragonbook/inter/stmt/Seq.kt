package dragonbook.inter.stmt

data class Seq(val head: Stmt, val tail: Stmt) : Stmt {

    override fun toString(): String = "{ $head; $tail }"
}