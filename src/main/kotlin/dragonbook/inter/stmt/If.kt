package dragonbook.inter.stmt

import dragonbook.error.ParseError
import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class If(val condition: Expr, val then: Stmt) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) throw ParseError("boolean required in if", condition)
    }

    override fun toString(): String = "if ($condition) { $then }"
}