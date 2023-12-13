package dragonbook.symbols

import dragonbook.lexer.Tag
import dragonbook.lexer.Word

interface Type : Word {
    val width: Int // width is used for storage allocation
    fun isNumeric(): Boolean = false

    companion object {
        val INT: Type = Primitive("int", 4)
        val FLOAT: Type = Primitive("float", 8)
        val CHAR: Type = Primitive("char", 1)
        val BOOL: Type = Primitive("bool", 1)
    }
}

data class Primitive(val name: String, override val width: Int) : Type {
    override val tag: Int get() = Tag.BASIC
    override val lexeme: String get() = name
    override fun isNumeric(): Boolean {
        return this == Type.CHAR || this == Type.INT || this == Type.FLOAT
    }
}