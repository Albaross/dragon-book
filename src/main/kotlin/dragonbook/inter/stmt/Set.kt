package dragonbook.inter.stmt

import dragonbook.error.ParseError
import dragonbook.inter.expr.Expr
import dragonbook.inter.expr.Id
import dragonbook.symbols.Type

data class Set(val id: Id, val expr: Expr) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (id.type.isNumeric() && expr.type.isNumeric()) expr.type
        else if (id.type == Type.BOOL && expr.type == Type.BOOL) expr.type
        else throw ParseError(node = this)
    }

    override fun toString(): String = "$id = $expr"
}