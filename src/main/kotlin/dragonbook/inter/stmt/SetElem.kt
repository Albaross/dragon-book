package dragonbook.inter.stmt

import dragonbook.inter.expr.Access
import dragonbook.inter.expr.Expr
import dragonbook.inter.expr.Id
import dragonbook.symbols.Array

data class SetElem(val access: Access, val expr: Expr) : Stmt {

    init {
        validate()
    }

    private fun validate() {
        if (access.type is Array || expr.type is Array) error("type error")
        else if (access.type == expr.type) expr.type
        else if (access.type.isNumeric() && expr.type.isNumeric()) expr.type
        else error("type error")
    }

    val array: Id get() = access.array
    val index: Expr get() = access.index

    override fun toString(): String = "$array[$index] = $expr"
}