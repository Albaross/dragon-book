package dragonbook.inter.stmt

import dragonbook.error.ParseError
import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class Do(val stmt: Stmt, val condition: Expr) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) throw ParseError("boolean required in do", condition)
    }

    override fun toString(): String = "do { $stmt } while ($condition)"
}