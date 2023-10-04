package symbols;

import lexer.*;

public record Array(int size, Type of) implements Type {

    @Override
    public String name() {
        return "[]";
    }

    @Override
    public int tag() {
        return Tag.INDEX;
    }

    @Override
    public int width() {
        return size * of.width();
    }

    @Override
    public String toString() {
        return "[" + size + "] " + of;
    }
}