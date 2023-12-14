package dragonbook.inter;

interface Node {
    fun error(s: String): Nothing {
        throw Error("near line 0: $s");
    }
}