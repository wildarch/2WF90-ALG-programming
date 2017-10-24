package group14

import group14.evaluation.REPL
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

fun replTest(input: String, expectedOutput: String) {
    val baos = ByteArrayOutputStream()
    val printStream = PrintStream(baos)

    val bais = ByteArrayInputStream(input.toByteArray())

    REPL(options = setOf(Option.SKIP_INTRO, Option.UNICODE), input = bais, output = printStream)
    val actual = baos.toString().lines().first()
    assertEquals(expectedOutput, actual)
}

fun replTest(tests: List<Pair<String, String>>) =
        tests.forEach({ (a, b) -> replTest(a, b)})

