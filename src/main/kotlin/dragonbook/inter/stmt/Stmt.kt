package dragonbook.inter.stmt;

import dragonbook.inter.Node;

public class Stmt extends Node {

    public static Stmt Null = new Stmt() {
        @Override
        public String toString() {
            return "null";
        }
    };
}