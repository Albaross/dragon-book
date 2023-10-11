package dragonbook.lexer;

public record Num(int value) implements Token {

    @Override
    public int tag() {
        return Tag.NUM;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}