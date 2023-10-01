package gen;

import inter.*;
import parser.*;

import java.io.*;
import java.util.*;

public class Generator {

    private Parser parse;

    public Generator(Parser p) {
        parse = p;
    }

    public List<String> gen() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Node.out = new PrintStream(buffer);
        parse.program();
        return Arrays.asList(buffer.toString().split("\r?\n"));
    }
}
