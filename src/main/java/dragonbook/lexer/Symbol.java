package dragonbook.lexer;

record Symbol(int tag) implements Token {

    @Override
    public String toString() {
        return String.valueOf((char) tag);
    }
}