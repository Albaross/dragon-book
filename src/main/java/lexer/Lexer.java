package lexer;

import java.io.*;
import java.util.*;

import symbols.*;

public class Lexer {
    private int line = 1;

    private final HashMap<String, Word> words = new HashMap<>();
    private final InputStream in;
    private char peek = ' ';

    private void reserve(Word w) {
        words.put(w.lexeme(), w);
    }

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

    public Token scan() {
        for (; ; readChar()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if (peek == '\n') line = line + 1;
            else break;
        }
        switch (peek) {
            case '&' -> {
                if (readChar('&')) return Word.AND;
                else return Token.AND;
            }
            case '|' -> {
                if (readChar('|')) return Word.OR;
                else return Token.OR;
            }
            case '=' -> {
                if (readChar('=')) return Word.EQ;
                else return Token.EQ;
            }
            case '!' -> {
                if (readChar('=')) return Word.NE;
                else return Token.NOT;
            }
            case '<' -> {
                if (readChar('=')) return Word.LE;
                else return Token.LT;
            }
            case '>' -> {
                if (readChar('=')) return Word.GE;
                else return Token.GT;
            }
        }
        if (Character.isDigit(peek)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(peek, 10);
                readChar();
            } while (Character.isDigit(peek));
            if (peek != '.') return new Num(value);
            float realValue = value;
            float div = 10;
            for (; ; ) {
                readChar();
                if (!Character.isDigit(peek)) break;
                realValue += (Character.digit(peek, 10) / div);
                div *= 10;
            }
            return new Real(realValue);
        }
        if (Character.isLetter(peek)) {
            final var builder = new StringBuilder();
            do {
                builder.append(peek);
                readChar();
            } while (Character.isLetterOrDigit(peek));
            String key = builder.toString();
            Word word = words.get(key);
            if (word != null) return word;
            word = new Word.Id(key);
            words.put(key, word);
            return word;
        }
        final Token tok = new Token.Symbol(peek);
        peek = ' ';
        return tok;
    }

    public int getLine() {
        return line;
    }
}