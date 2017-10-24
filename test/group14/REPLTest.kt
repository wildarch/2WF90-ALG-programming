package group14

import group14.evaluation.REPL
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class REPLTest {

    companion object {

        @JvmStatic
        fun replTest(input: String, expectedOutput: String) {
            val baos = ByteArrayOutputStream()
            val printStream = PrintStream(baos)

            REPL(options = setOf(Option.SKIP_INTRO), input = System.`in`, output = printStream)
            val actual = baos.toString().lines().first()
            assertEquals(expectedOutput, actual)
        }
    }
}

