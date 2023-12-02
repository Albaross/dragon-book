package main;

import java.io.*;

import lexer.*;
import parser.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try (final var in = new FileInputStream("src/test/resources/test")) {
            Lexer lex = new Lexer(in);
            Parser parse = new Parser(lex);
            parse.program();
            System.out.write('\n');
        }
    }
}