package dragonbook.gen;

import dragonbook.inter.expr.Expr;
import dragonbook.lexer.Token
import dragonbook.lexer.Word;
import dragonbook.symbols.Type;

data class Temp(override val type: Type, val number: Int) : Expr {
    override val op: Token get() = Word.TEMP
    override fun toString(): String = "t$number"
}