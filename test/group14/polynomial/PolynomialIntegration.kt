package group14.polynomial

import group14.Option
import group14.evaluation.REPL
import group14.replTest
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class PolynomialIntegration {
    @Test
    fun sum() {
        val tests = listOf(
                Pair("[2X + 3X^2 + 1] + [X + 2X^2 + 1] (mod 5)", "3X+2 (ℤ/5ℤ)"),
                Pair("[2X + 3X^2 + 1] + [X + 2X^2 + 1] (mod 5)", "3X+2 (ℤ/5ℤ)"),
                Pair("[2X + 3X^2 + 1] + [X + 2X^2 + 1] (mod 5)", "3X+2 (ℤ/5ℤ)"),
                Pair("[2X + 3X^2 + 1] + [X + 2X^2 + 1] (mod 5)", "3X+2 (ℤ/5ℤ)")
        )

        for ((input, expected) in tests) {
            replTest(input, expected)
        }

    }

}