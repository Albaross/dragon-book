package dragonbook.lexer

import dragonbook.test.EXPECTED_LEXER_OUTPUT
import java.io.FileInputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {

    @Test
    fun `code is scanned correctly`() {
        FileInputStream("src/test/resources/test").use {
            assertEquals(EXPECTED_LEXER_OUTPUT, Lexer(it).toList())
        }
    }
}