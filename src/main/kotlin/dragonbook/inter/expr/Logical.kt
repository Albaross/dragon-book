package dragonbook.inter.expr

import dragonbook.error.ParseError
import dragonbook.symbols.Type

interface Logical : Expr {

    val expr1: Expr
    val expr2: Expr

    override val type: Type get() = Type.BOOL

    fun validate() {
        if (expr1.type != Type.BOOL || expr2.type != Type.BOOL) throw ParseError(node = this)
    }
}