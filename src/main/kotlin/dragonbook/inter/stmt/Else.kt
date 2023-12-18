package dragonbook.inter.stmt

import dragonbook.error.ParseError
import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class Else(val condition: Expr, val then: Stmt, val elseStmt: Stmt) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (condition.type != Type.BOOL) throw ParseError("boolean required in if", condition)
    }

    override fun toString(): String = "if ($condition) { $then } else { $elseStmt }"
}