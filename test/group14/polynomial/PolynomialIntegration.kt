package group14.polynomial

import group14.evaluation.REPL
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class PolynomialIntegration {
    @Test
    fun sum() {
        val input = "[2X + 3X^2 + 1] + [X + 1X^2 + 1] mod 4"
        val reader = input.reader()

        val baos = ByteArrayOutputStream()
        val printStream = PrintStream(baos)

        val repl = REPL(input = reader.buffered(), output = printStream)
        val expected = ">>> 3X + 2"
        val actual = baos.toString()
        assertEquals(expected, actual)
    }
}