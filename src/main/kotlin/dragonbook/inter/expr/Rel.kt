package dragonbook.inter.expr

import dragonbook.error.ParseError
import dragonbook.lexer.Token
import dragonbook.symbols.Array

data class Rel(
    override val op: Token,
    override val expr1: Expr,
    override val expr2: Expr
) : Logical {

    init {
        validate()
    }

    override fun validate() {
        if (expr1.type is Array || expr2.type is Array || expr1.type != expr2.type) throw ParseError(node = this)
    }

    override fun toString(): String = "$expr1 $op $expr2"
}