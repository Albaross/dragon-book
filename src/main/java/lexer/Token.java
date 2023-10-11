package lexer;

public interface Token {

    int tag();

    Token AND = new Symbol('&');
    Token OR = new Symbol('|');
    Token EQ = new Symbol('=');
    Token NOT = new Symbol('!');
    Token LT = new Symbol('<');
    Token GT = new Symbol('>');
    Token PLUS = new Symbol('+');
    Token TIMES = new Symbol('*');
}