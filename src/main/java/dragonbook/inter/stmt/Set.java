package dragonbook.inter.stmt;

import dragonbook.error.ParseError;
import dragonbook.inter.expr.Expr;
import dragonbook.inter.expr.Id;
import dragonbook.symbols.Type;

public record Set(Id id, Expr expr) implements Stmt {

    public Set {
        checkTypes(id.type(), expr.type());
    }

    private void checkTypes(Type type1, Type type2) {
        if (type1.isNumeric() && type2.isNumeric()) return;
        else if (type1 == Type.BOOL && type2 == Type.BOOL) return;
        throw new ParseError("type error");
    }
}