package dragonbook.lexer;

public interface Word extends Token {

    String lexeme();

    Word AND = new Keyword("&&", Tag.AND);
    Word OR = new Keyword("||", Tag.OR);
    Word EQ = new Keyword("==", Tag.EQ);
    Word NE = new Keyword("!=", Tag.NE);
    Word LE = new Keyword("<=", Tag.LE);
    Word GE = new Keyword(">=", Tag.GE);
    Word MINUS = new Keyword("minus", Tag.MINUS);
    Word TRUE = new Keyword("true", Tag.TRUE);
    Word FALSE = new Keyword("false", Tag.FALSE);
    Word TEMP = new Keyword("t", Tag.TEMP);
    Word IF = new Keyword("if", Tag.IF);
    Word ELSE = new Keyword("else", Tag.ELSE);
    Word WHILE = new Keyword("while", Tag.WHILE);
    Word DO = new Keyword("do", Tag.DO);
    Word BREAK = new Keyword("break", Tag.BREAK);
    Word ACCESS = new Keyword("[]", Tag.INDEX);
}