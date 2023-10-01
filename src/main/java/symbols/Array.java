package symbols;

import lexer.*;

public class Array extends Type {
    public final Type of; // array *of* type
    private final int size; // number of elements

    public Array(int size, Type of) {
        super("[]", Tag.INDEX, size * of.width);
        this.size = size;
        this.of = of;
    }

    @Override
    public String toString() {
        return "[" + size + "] " + of;
    }
}