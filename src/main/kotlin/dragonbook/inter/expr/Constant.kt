package dragonbook.inter.expr

import dragonbook.lexer.Num
import dragonbook.lexer.Token
import dragonbook.lexer.Word
import dragonbook.symbols.Type

data class Constant(val value: Token, override val type: Type) : Expr {

    constructor(value: Int) : this(Num(value), Type.INT)

    override val op: Token get() = value

    override fun toString(): String = op.toString()

    companion object {
        val TRUE = Constant(Word.TRUE, Type.BOOL)
        val FALSE = Constant(Word.FALSE, Type.BOOL)
    }
}