package symbols;

import java.util.*;

import inter.expr.*;
import lexer.*;

public class Env {
    private final HashMap<Token, Id> table = new HashMap<>();
    private final Env prev;

    public Env(Env prev) {
        this.prev = prev;
    }

    public void put(Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            final Id found = e.table.get(w);
            if (found != null) return found;
        }
        return null;
    }
}