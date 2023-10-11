package dragonbook.lexer;

record Name(String lexeme) implements Word {

    @Override
    public int tag() {
        return Tag.ID;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}