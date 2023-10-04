package lexer;

public class Token {
    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return String.valueOf((char) tag);
    }

    public static final Token AND = new Token('&');
    public static final Token OR = new Token('|');
    public static final Token EQ = new Token('=');
    public static final Token NOT = new Token('!');
    public static final Token LT = new Token('<');
    public static final Token GT = new Token('>');
    public static final Token PLUS = new Token('+');
    public static final Token TIMES = new Token('*');
}