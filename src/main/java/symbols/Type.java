package symbols;

import lexer.Tag;
import lexer.Word;

public class Type extends Word {

    public final int width; // width is used for storage allocation

    public Type(String name, int tag, int width) {
        super(name, tag);
        this.width = width;
    }

    public boolean isNumeric() {
        return this == Type.CHAR || this == Type.INT || this == Type.FLOAT;
    }

    public static final Type INT = new Type("int", Tag.BASIC, 4);
    public static final Type FLOAT = new Type("float", Tag.BASIC, 8);
    public static final Type CHAR = new Type("char", Tag.BASIC, 1);
    public static final Type BOOL = new Type("bool", Tag.BASIC, 1);
}