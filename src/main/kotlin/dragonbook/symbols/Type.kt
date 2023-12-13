package dragonbook.symbols;

import dragonbook.lexer.Tag;
import dragonbook.lexer.Word;

public class Type extends Word {
    public int width; // width is used for storage allocation

    public Type(String name, int tag, int width) {
        super(name, tag);
        this.width = width;
    }

    public boolean isNumeric() {
        return this == Type.CHAR || this == Type.INT || this == Type.FLOAT;
    }

    public static Type max(Type type1, Type type2) {
        if (!type1.isNumeric() || !type2.isNumeric()) return null;
        else if (type1 == Type.FLOAT || type2 == Type.FLOAT) return Type.FLOAT;
        else if (type1 == Type.INT || type2 == Type.INT) return Type.INT;
        else return Type.CHAR;
    }

    public static final Type INT = new Type("int", Tag.BASIC, 4);
    public static final Type FLOAT = new Type("float", Tag.BASIC, 8);
    public static final Type CHAR = new Type("char", Tag.BASIC, 1);
    public static final Type BOOL = new Type("bool", Tag.BASIC, 1);
}