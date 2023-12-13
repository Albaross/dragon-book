package dragonbook.symbols;

import dragonbook.lexer.Tag;

public class Array extends Type {
    public final Type of; // array *of* type
    public final int size; // number of elements

    public Array(int size, Type of) {
        super("[]", Tag.INDEX, size * of.width);
        this.size = size;
        this.of = of;
    }

    public String toString() {
        return of + "[" + size + "]";
    }
}