package dragonbook.inter.stmt

import dragonbook.inter.Node

interface Stmt : Node {

    companion object {
        val NULL: Stmt = Null
    }
}

object Null : Stmt {
    override fun toString(): String = "null"
}