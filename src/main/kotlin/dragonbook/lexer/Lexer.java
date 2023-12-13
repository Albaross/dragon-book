package dragonbook.lexer;

import dragonbook.symbols.Type;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;

public class Lexer {
    public static int lineNumber = 1;
    private final InputStream in;
    private final HashMap<String, Word> words = new HashMap<>();
    private char peek = ' ';

    public Lexer(InputStream in) {
        this.in = in;
        reserve(Word.IF);
        reserve(Word.ELSE);
        reserve(Word.WHILE);
        reserve(Word.DO);
        reserve(Word.BREAK);
        reserve(Word.TRUE);
        reserve(Word.FALSE);
        reserve(Type.INT);
        reserve(Type.CHAR);
        reserve(Type.BOOL);
        reserve(Type.FLOAT);
    }

    public Token scan() {
        consumeWhitespaces();
        if (Character.isDigit(peek)) {
            return readNumber();
        }
        if (Character.isLetter(peek)) {
            return readWord();
        }
        final Token tok = readSymbol();
        peek = ' ';
        return tok;
    }

    private void consumeWhitespaces() {
        for (; ; readChar()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if (peek == '\n') lineNumber = lineNumber + 1;
            else break;
        }
    }

    private Token readNumber() {
        int value = 0;
        do {
            value = 10 * value + Character.digit(peek, 10);
            readChar();
        } while (Character.isDigit(peek));
        if (peek != '.') return new Num(value);
        float decimal = value;
        float divider = 10;
        for (; ; ) {
            readChar();
            if (!Character.isDigit(peek)) break;
            decimal += Character.digit(peek, 10) / divider;
            divider *= 10;
        }
        return new Real(decimal);
    }

    private Word readWord() {
        final var builder = new StringBuilder();
        do {
            builder.append(peek);
            readChar();
        } while (Character.isLetterOrDigit(peek));
        final String lexeme = builder.toString();
        Word word = words.get(lexeme);
        if (word != null) return word;
        word = reserve(new Word(lexeme, Tag.ID));
        return word;
    }

    private Token readSymbol() {
        switch (peek) {
            case '&':
                if (readChar('&')) return Word.AND;
                else return Token.AND;
            case '|':
                if (readChar('|')) return Word.OR;
                else return Token.OR;
            case '=':
                if (readChar('=')) return Word.EQ;
                else return Token.EQ;
            case '!':
                if (readChar('=')) return Word.NE;
                else return Token.NOT;
            case '<':
                if (readChar('=')) return Word.LE;
                else return Token.LT;
            case '>':
                if (readChar('=')) return Word.GE;
                else return Token.GT;
            default:
                return new Token(peek);
        }
    }

    private void readChar() {
        try {
            peek = (char) in.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private boolean readChar(char c) {
        readChar();
        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    private Word reserve(Word word) {
        words.put(word.lexeme, word);
        return word;
    }
}