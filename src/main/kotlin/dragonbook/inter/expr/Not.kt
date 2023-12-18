package dragonbook.inter.expr

import dragonbook.error.ParseError
import dragonbook.lexer.Token
import dragonbook.symbols.Type

data class Not(override val op: Token, val expr: Expr) : Logical {

    init {
        validate()
    }

    override fun validate() {
        if (expr.type != Type.BOOL) throw ParseError(node = this)
    }

    override val expr1: Expr get() = expr
    override val expr2: Expr get() = expr

    override fun toString(): String = "$op $expr"
}