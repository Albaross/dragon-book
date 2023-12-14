package dragonbook.lexer

interface Word : Token {
    val lexeme: String

    companion object {
        val AND: Word = Keyword("&&", Tag.AND)
        val OR: Word = Keyword("||", Tag.OR)
        val EQ: Word = Keyword("==", Tag.EQ)
        val NE: Word = Keyword("!=", Tag.NE)
        val LE: Word = Keyword("<=", Tag.LE)
        val GE: Word = Keyword(">=", Tag.GE)
        val MINUS: Word = Keyword("minus", Tag.MINUS)
        val TRUE: Word = Keyword("true", Tag.TRUE)
        val FALSE: Word = Keyword("false", Tag.FALSE)
        val TEMP: Word = Keyword("t", Tag.TEMP)
        val IF: Word = Keyword("if", Tag.IF)
        val ELSE: Word = Keyword("else", Tag.ELSE)
        val WHILE: Word = Keyword("while", Tag.WHILE)
        val DO: Word = Keyword("do", Tag.DO)
        val BREAK: Word = Keyword("break", Tag.BREAK)
        val INDEX: Word = Keyword("[]", Tag.INDEX)
    }
}

data class Name(override val lexeme: String) : Word {
    override val tag: Int get() = Tag.ID
    override fun toString(): String = lexeme
}

data class Keyword(override val lexeme: String, override val tag: Int) : Word {
    override fun toString(): String = lexeme
}