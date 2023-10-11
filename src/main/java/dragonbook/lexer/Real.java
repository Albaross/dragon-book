package dragonbook.lexer;

public record Real(float value) implements Token {

    @Override
    public int tag() {
        return Tag.REAL;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}