package dragonbook.parser

import dragonbook.test.EXPECTED_LEXER_OUTPUT
import dragonbook.test.EXPECTED_PARSER_OUTPUT
import java.io.FileInputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {

    @Test
    fun `code is parsed correctly`() {
        FileInputStream("src/test/resources/test").use {
            val result = Parser(EXPECTED_LEXER_OUTPUT.iterator()::next).program()
            assertEquals(EXPECTED_PARSER_OUTPUT, result)
        }
    }
}