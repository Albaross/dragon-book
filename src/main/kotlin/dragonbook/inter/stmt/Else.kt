package dragonbook.inter.stmt

import dragonbook.inter.expr.Expr
import dragonbook.symbols.Type

data class Else(val condition: Expr, val then: Stmt, val elseStmt: Stmt) : Stmt {

    init {
        if (condition.type != Type.BOOL) condition.error("boolean required in if")
    }

    override fun toString(): String = "if ($condition) $then else $elseStmt"
}