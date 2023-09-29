package parser;

import lexer.Lexer;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParserTest {

    @Test
    public void codeIsGeneratedCorrectly() throws IOException {
        final String path = "src/test/resources/test";
        try (final InputStream in = new FileInputStream(path)) {
            final Lexer lex = new Lexer(in);
            final Parser parse = new Parser(lex);
            parse.program();
        }
    }
}
