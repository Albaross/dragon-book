package dragonbook.inter.stmt

data class Seq(val head: Stmt, val tail: Stmt) : Stmt {

    override fun toString(): String = when {
        head != Stmt.NULL && tail != Stmt.NULL -> "$head; $tail"
        head != Stmt.NULL -> head.toString()
        tail != Stmt.NULL -> tail.toString()
        else -> ""
    }
}

fun seqOf(vararg stmts: Stmt): Seq {
    if (stmts.isEmpty()) return Seq(head = Stmt.NULL, tail = Stmt.NULL)

    var tail = Stmt.NULL
    for (stmt in stmts.reversed()) {
        tail = Seq(head = stmt, tail = tail)
    }

    return tail as Seq
}