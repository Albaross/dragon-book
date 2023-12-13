package dragonbook.lexer

data class Num(val value: Int) : Token {

    override val tag: Int get() = Tag.NUM
    override fun toString(): String = value.toString()
}