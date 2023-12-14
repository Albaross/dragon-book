package dragonbook.inter.expr

import dragonbook.lexer.Token
import dragonbook.lexer.Word
import dragonbook.symbols.Type

data class Access(
    val array: Id,
    val index: Expr,
    override val type: Type // type is element type after
) : Op {

    override val op: Token get() = Word.INDEX // flattening the array

    override fun toString(): String = "$array[$index]"
}