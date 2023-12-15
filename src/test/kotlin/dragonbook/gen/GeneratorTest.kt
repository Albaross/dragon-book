package dragonbook.gen

import dragonbook.test.EXPECTED_GENERATOR_OUTPUT
import dragonbook.test.EXPECTED_PARSER_OUTPUT
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratorTest {

    @Test
    fun `code is generated correctly`() {
        assertEquals(EXPECTED_GENERATOR_OUTPUT, Generator { EXPECTED_PARSER_OUTPUT }.gen())
    }
}