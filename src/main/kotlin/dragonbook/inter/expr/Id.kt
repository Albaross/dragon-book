package dragonbook.inter.expr

import dragonbook.lexer.Token
import dragonbook.lexer.Word
import dragonbook.symbols.Type

data class Id(
    val id: Word,
    override val type: Type,
    val offset: Int // relative address
) : Expr {

    override val op: Token get() = id
    override fun toString(): String = op.toString()
}