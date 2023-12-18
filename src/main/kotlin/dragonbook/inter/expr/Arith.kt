package dragonbook.inter.expr

import dragonbook.error.ParseError
import dragonbook.lexer.Token
import dragonbook.symbols.Type

data class Arith(
    override val op: Token,
    val expr1: Expr,
    val expr2: Expr
) : Op {

    init {
        validate()
    }

    private fun validate() {
        if (!expr1.type.isNumeric() || !expr2.type.isNumeric()) throw ParseError(node = this)
    }

    override val type: Type
        get() =
            if (expr1.type == Type.FLOAT || expr2.type == Type.FLOAT) Type.FLOAT
            else if (expr1.type == Type.INT || expr2.type == Type.INT) Type.INT
            else Type.CHAR

    override fun toString(): String = "$expr1 $op $expr2"
}