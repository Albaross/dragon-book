package dragonbook.inter.stmt

import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class While(val condition: Expr, val stmt: Stmt) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) condition.error("boolean required in while")
    }

    override fun toString(): String = "while ($condition) { $stmt }"
}