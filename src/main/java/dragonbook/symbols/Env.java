package dragonbook.symbols;

import dragonbook.inter.expr.Id;
import dragonbook.lexer.Token;

import java.util.HashMap;

public class Env {
    private final HashMap<Token, Id> table;
    protected Env prev;

    public Env(Env n) {
        table = new HashMap<>();
        prev = n;
    }

    public void put(Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            Id found = e.table.get(w);
            if (found != null) return found;
        }
        return null;
    }
}