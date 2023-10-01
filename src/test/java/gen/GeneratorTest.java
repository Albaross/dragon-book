package gen;

import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GeneratorTest {

    @Test
    public void codeIsGeneratedCorrectly() throws IOException {
        try (final var in = new FileInputStream("src/test/resources/test")) {
            Lexer lex = new Lexer(in);
            Parser parse = new Parser(lex);
            Generator gen = new Generator(parse);

            Assertions.assertEquals(LINES, gen.gen());
        }
    }

    private static final List<String> LINES = List.of(
            "L1:L3:\ti = i + 1",
            "L5:\tt1 = i * 8",
            "\tt2 = a[t1]",
            "\tif t2 < v goto L3",
            "L4:\tj = j - 1",
            "L7:\tt3 = j * 8",
            "\tt4 = a[t3]",
            "\tif t4 > v goto L4",
            "L6:\tiffalse i >= j goto L8",
            "L9:\tgoto L2",
            "L8:\tt5 = i * 8",
            "\tx = a[t5]",
            "L10:\tt6 = i * 8",
            "\tt7 = j * 8",
            "\tt8 = a[t7]",
            "\ta[t6] = t8",
            "L11:\tt9 = j * 8",
            "\ta[t9] = x",
            "\tgoto L1",
            "L2:"
    );
}
