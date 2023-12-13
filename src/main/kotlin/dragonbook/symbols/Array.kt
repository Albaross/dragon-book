package dragonbook.symbols

import dragonbook.lexer.Tag

data class Array(
    val of: Type, // array *of* type
    val size: Int // number of elements
) : Type {

    override val tag: Int get() = Tag.INDEX
    override val lexeme: String get() = "[]"
    override val width: Int get() = size * of.width
    override fun toString(): String = "$of[$size]"

}