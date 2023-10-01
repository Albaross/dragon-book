package lexer;

import java.io.*;
import java.util.*;

import symbols.*;

public class Lexer {
    public static int line = 1;

    private final InputStream in;
    private char peek = ' ';
    private final HashMap<String, Word> words = new HashMap<>();

    void reserve(Word w) {
        words.put(w.lexeme, w);
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

    void readChar() throws IOException {
        peek = (char) in.read();
    }

    boolean readChar(char c) throws IOException {
        readChar();
        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    public Token scan() throws IOException {
        for (; ; readChar()) {
            if (peek == ' ' || peek == '\t' || peek == '\r') continue;
            else if (peek == '\n') line = line + 1;
            else break;
        }
        switch (peek) {
            case '&' -> {
                if (readChar('&')) return Word.AND;
                else return new Token('&');
            }
            case '|' -> {
                if (readChar('|')) return Word.OR;
                else return new Token('|');
            }
            case '=' -> {
                if (readChar('=')) return Word.EQ;
                else return new Token('=');
            }
            case '!' -> {
                if (readChar('=')) return Word.NE;
                else return new Token('!');
            }
            case '<' -> {
                if (readChar('=')) return Word.LE;
                else return new Token('<');
            }
            case '>' -> {
                if (readChar('=')) return Word.GE;
                else return new Token('>');
            }
        }
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readChar();
            } while (Character.isDigit(peek));
            if (peek != '.') return new Num(v);
            float x = v;
            float d = 10;
            for (; ; ) {
                readChar();
                if (!Character.isDigit(peek)) break;
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x);
        }
        if (Character.isLetter(peek)) {
            StringBuilder b = new StringBuilder();
            do {
                b.append(peek);
                readChar();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = words.get(s);
            if (w != null) return w;
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        Token tok = new Token(peek);
        peek = ' ';
        return tok;
    }
}