package dragonbook.inter.expr

import dragonbook.error.ParseError
import dragonbook.lexer.Token
import dragonbook.symbols.Type

data class Unary(override val op: Token, val expr: Expr) : Op { // handles minus, for ! see Not

    init {
        validate()
    }

    private fun validate() {
        if (!expr.type.isNumeric()) throw ParseError(node = this)
    }

    override val type: Type
        get() = if (expr.type == Type.FLOAT) Type.FLOAT else Type.INT

    override fun toString(): String = "$op $expr"
}