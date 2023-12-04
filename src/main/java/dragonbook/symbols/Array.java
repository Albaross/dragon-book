package dragonbook.symbols;

import dragonbook.lexer.Tag;

public class Array extends Type {
    public Type of; // array *of* type
    public int size; // number of elements

    public Array(int sz, Type p) {
        super("[]", Tag.INDEX, sz * p.width);
        size = sz;
        of = p;
    }

    public String toString() {
        return of + "[" + size + "]";
    }
}