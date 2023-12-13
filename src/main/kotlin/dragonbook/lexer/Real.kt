package dragonbook.lexer

data class Real(val value: Float) : Token {

    override val tag: Int get() = Tag.REAL
    override fun toString(): String = value.toString()
}