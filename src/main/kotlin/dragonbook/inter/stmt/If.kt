package dragonbook.inter.stmt

import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class If(val condition: Expr, val then: Stmt) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) condition.error("boolean required in if")
    }

    override fun toString(): String = "if ($condition) $then"
}