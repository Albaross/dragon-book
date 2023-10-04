package lexer;

public class Word extends Token {
    public final String lexeme;

    public Word(String lexeme, int tag) {
        super(tag);
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }

    public static final Word AND = new Word("&&", Tag.AND);
    public static final Word OR = new Word("||", Tag.OR);
    public static final Word EQ = new Word("==", Tag.EQ);
    public static final Word NE = new Word("!=", Tag.NE);
    public static final Word LE = new Word("<=", Tag.LE);
    public static final Word GE = new Word(">=", Tag.GE);
    public static final Word MINUS = new Word("minus", Tag.MINUS);
    public static final Word TRUE = new Word("true", Tag.TRUE);
    public static final Word FALSE = new Word("false", Tag.FALSE);
    public static final Word TEMP = new Word("t", Tag.TEMP);
    public static final Word IF = new Word("if", Tag.IF);
    public static final Word ELSE = new Word("else", Tag.ELSE);
    public static final Word WHILE = new Word("while", Tag.WHILE);
    public static final Word DO = new Word("do", Tag.DO);
    public static final Word BREAK = new Word("break", Tag.BREAK);
    public static final Word ACCESS = new Word("[]", Tag.INDEX);
}