package dragonbook.integration

import dragonbook.gen.Generator
import dragonbook.lexer.Lexer
import dragonbook.parser.Parser
import dragonbook.test.EXPECTED_GENERATOR_OUTPUT
import java.io.FileInputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class IntegrationTest {

    @Test
    fun `code is scanned, parsed and generated correctly`() {
        FileInputStream("src/test/resources/test").use {
            val lex = Lexer(it)
            val parse = Parser(lex)
            val gen = Generator(parse)

            val result = gen.gen()
            result.forEach(::println)
            assertEquals(EXPECTED_GENERATOR_OUTPUT, result)
        }
    }
}

