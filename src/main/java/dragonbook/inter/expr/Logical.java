package dragonbook.inter.expr;

import dragonbook.error.ParseError;
import dragonbook.symbols.Type;

public interface Logical extends Expr {

    @Override
    default Type type() {
        return Type.BOOL;
    }

    Expr expr1();

    Expr expr2();

    default void checkTypes() {
        if (expr1().type() != Type.BOOL || expr2().type() != Type.BOOL) throw new ParseError("type error");
    }
}