package dragonbook.inter.stmt;

import dragonbook.inter.Node;

public interface Stmt extends Node {
    Stmt NULL = new Stmt() {
    };
}