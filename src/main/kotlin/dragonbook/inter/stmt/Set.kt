package dragonbook.inter.stmt

import dragonbook.inter.expr.Expr
import dragonbook.inter.expr.Id
import dragonbook.symbols.Type

data class Set(val id: Id, val expr: Expr) : Stmt {

    init {
        if (check(id.type, expr.type) == null) error("type error")
    }

    private fun check(type1: Type, type2: Type): Type? {
        return if (type1.isNumeric() && type2.isNumeric()) type2
        else if (type1 == Type.BOOL && type2 == Type.BOOL) type2
        else null
    }

    override fun toString(): String = "$id = $expr"
}