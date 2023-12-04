package dragonbook.symbols;

import dragonbook.lexer.Tag;
import dragonbook.lexer.Word;

public class Type extends Word {
    public int width; // width is used for storage allocation

    public Type(String s, int tag, int w) {
        super(s, tag);
        width = w;
    }

    public static boolean numeric(Type p) {
        return p == Type.CHAR || p == Type.INT || p == Type.FLOAT;
    }

    public static Type max(Type p1, Type p2) {
        if (!numeric(p1) || !numeric(p2)) return null;
        else if (p1 == Type.FLOAT || p2 == Type.FLOAT) return Type.FLOAT;
        else if (p1 == Type.INT || p2 == Type.INT) return Type.INT;
        else return Type.CHAR;
    }

    public static final Type INT = new Type("int", Tag.BASIC, 4);
    public static final Type FLOAT = new Type("float", Tag.BASIC, 8);
    public static final Type CHAR = new Type("char", Tag.BASIC, 1);
    public static final Type BOOL = new Type("bool", Tag.BASIC, 1);
}