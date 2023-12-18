package dragonbook.inter.stmt

import dragonbook.error.ParseError
import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class While(val condition: Expr, val stmt: Stmt) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) throw ParseError("boolean required in while", condition)
    }

    override fun toString(): String = "while ($condition) { $stmt }"
}