package lexer;

record Keyword(String lexeme, int tag) implements Word {

    @Override
    public String toString() {
        return lexeme;
    }
}