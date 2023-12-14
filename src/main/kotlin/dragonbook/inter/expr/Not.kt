package dragonbook.inter.expr

import dragonbook.lexer.Token

data class Not(override val op: Token, val expr: Expr) : Logical {

    override val expr1: Expr get() = expr
    override val expr2: Expr get() = expr

    override fun toString(): String = "$op $expr1"
}