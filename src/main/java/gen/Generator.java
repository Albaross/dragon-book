package gen;

import inter.*;
import parser.*;

import java.io.*;
import java.util.*;

public class Generator {

    private final Parser parse;

    public Generator(Parser parse) {
        this.parse = parse;
    }

    public List<String> gen() throws IOException {
        final var buffer = new ByteArrayOutputStream();
        Node.out = new PrintStream(buffer);
        parse.program();
        return Arrays.asList(buffer.toString().split("\r?\n"));
    }
}
