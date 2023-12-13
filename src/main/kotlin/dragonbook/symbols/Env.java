package dragonbook.symbols;

import dragonbook.inter.expr.Id;
import dragonbook.lexer.Token;

import java.util.HashMap;

public class Env {
    private final HashMap<Token, Id> table = new HashMap<>();
    private final Env prev;

    public Env(Env prev) {
        this.prev = prev;
    }

    public void put(Token tok, Id id) {
        table.put(tok, id);
    }

    public Id get(Token tok) {
        for (Env env = this; env != null; env = env.prev) {
            Id found = env.table.get(tok);
            if (found != null) return found;
        }
        return null;
    }
}