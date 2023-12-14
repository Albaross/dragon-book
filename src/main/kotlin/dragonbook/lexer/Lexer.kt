package dragonbook.lexer

import dragonbook.symbols.Type
import java.io.InputStream

class Lexer(private val source: InputStream) {
    private val words = HashMap<String, Word>()
    private var peek = ' '
    private var lineNumber = 1

    init {
        reserve(Word.IF)
        reserve(Word.ELSE)
        reserve(Word.WHILE)
        reserve(Word.DO)
        reserve(Word.BREAK)
        reserve(Word.TRUE)
        reserve(Word.FALSE)
        reserve(Type.INT)
        reserve(Type.CHAR)
        reserve(Type.BOOL)
        reserve(Type.FLOAT)
    }

    fun scan(): Token {
        consumeWhitespaces()
        if (Character.isDigit(peek)) {
            return readNumber()
        }
        if (Character.isLetter(peek)) {
            return readWord()
        }
        val tok = readSymbol()
        peek = ' '
        return tok
    }

    private fun consumeWhitespaces() {
        while (true) {
            if (peek == '\n') lineNumber += 1
            else if (peek != ' ' && peek != '\t' && peek != '\r') break
            readChar()
        }
    }

    private fun readNumber(): Token {
        var value = 0
        do {
            value = 10 * value + Character.digit(peek, 10)
            readChar()
        } while (Character.isDigit(peek))
        if (peek != '.') return Num(value)
        var decimal = value.toFloat()
        var divider = 10
        while (true) {
            readChar()
            if (!Character.isDigit(peek)) break
            decimal += Character.digit(peek, 10) / divider
            divider *= 10
        }
        return Real(decimal)
    }

    private fun readWord(): Word {
        val builder = StringBuilder()
        do {
            builder.append(peek)
            readChar()
        } while (Character.isLetterOrDigit(peek))
        val lexeme = builder.toString()
        var word = words[lexeme]
        if (word != null) return word
        word = reserve(Name(lexeme))
        return word
    }

    private fun readSymbol(): Token {
        when (peek) {
            '&' ->
                return if (readChar('&')) Word.AND
                else Token.AND

            '|' ->
                return if (readChar('|')) Word.OR
                else Token.OR

            '=' ->
                return if (readChar('=')) Word.EQ
                else Token.EQ

            '!' ->
                return if (readChar('=')) Word.NE
                else Token.NOT

            '<' ->
                return if (readChar('=')) Word.LE
                else Token.LT

            '>' ->
                return if (readChar('=')) Word.GE
                else Token.GT

            else ->
                return Symbol(peek.code)
        }
    }

    private fun readChar() {
        peek = source.read().toChar()
    }

    private fun readChar(c: Char): Boolean {
        readChar()
        if (peek != c) return false
        peek = ' '
        return true
    }

    private fun reserve(word: Word): Word {
        words[word.lexeme] = word
        return word
    }
}