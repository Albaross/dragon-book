package dragonbook.inter.expr

import dragonbook.lexer.Token

data class Or(
    override val op: Token,
    override val expr1: Expr,
    override val expr2: Expr
) : Logical {

    init {
        check()
    }

    override fun toString(): String = "$expr1 $op $expr2"
}