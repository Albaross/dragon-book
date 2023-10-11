package symbols;

import lexer.Tag;
import lexer.Word;

public interface Type extends Word {

    @Override
    default String lexeme() {
        return name();
    }

    @Override
    default int tag() {
        return Tag.BASIC;
    }

    String name();

    int width(); // width is used for storage allocation


    default boolean isNumeric() {
        return false;
    }

    Type INT = new Numeric("int", 4);
    Type FLOAT = new Numeric("float", 8);
    Type CHAR = new Numeric("char", 1);
    Type BOOL = new Bool();

    record Numeric(String name, int width) implements Type {
        @Override
        public boolean isNumeric() {
            return true;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    record Bool() implements Type {
        @Override
        public String name() {
            return "bool";
        }

        @Override
        public int width() {
            return 1;
        }

        @Override
        public String toString() {
            return name();
        }
    }
}