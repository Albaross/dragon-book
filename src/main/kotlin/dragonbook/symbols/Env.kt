package dragonbook.symbols

import dragonbook.inter.expr.Id
import dragonbook.lexer.Token

class Env(private val prev: Env?) {
    private val table = HashMap<Token, Id>()

    operator fun set(tok: Token, id: Id) {
        table[tok] = id
    }

    operator fun get(tok: Token): Id? {
        var env: Env? = this
        while (env != null) {
            val found = env.table[tok]
            if (found != null) return found
            env = env.prev
        }
        return null
    }
}