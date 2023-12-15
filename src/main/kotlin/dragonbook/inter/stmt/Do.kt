package dragonbook.inter.stmt

import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class Do(val stmt: Stmt, val condition: Expr) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) condition.error("boolean required in do")
    }

    override fun toString(): String = "do { $stmt } while ($condition)"
}