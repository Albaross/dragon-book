package dragonbook.inter.stmt

import dragonbook.inter.expr.Access
import dragonbook.inter.expr.Expr
import dragonbook.inter.expr.Id
import dragonbook.symbols.Array
import dragonbook.symbols.Type

data class SetElem(val access: Access, val expr: Expr) : Stmt {

    init {
        if (check(access.type, expr.type) == null) error("type error")
    }

    val array: Id get() = access.array
    val index: Expr get() = access.index

    private fun check(type1: Type, type2: Type): Type? {
        return if (type1 is Array || type2 is Array) null
        else if (type1 == type2) type2
        else if (type1.isNumeric() && type2.isNumeric()) type2
        else null
    }

    override fun toString(): String = "$array[$index] = $expr"
}