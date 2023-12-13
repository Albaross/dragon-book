package dragonbook.lexer

interface Token {
    val tag: Int

    companion object {
        val AND: Token = Symbol('&')
        val OR: Token = Symbol('|')
        val EQ: Token = Symbol('=')
        val NOT: Token = Symbol('!')
        val LT: Token = Symbol('<')
        val GT: Token = Symbol('>')
        val PLUS: Token = Symbol('+')
        val TIMES: Token = Symbol('*')
    }
}

data class Symbol(override val tag: Int) : Token {
    constructor(tag: Char) : this(tag.code)

    override fun toString(): String = tag.toChar().toString()
}