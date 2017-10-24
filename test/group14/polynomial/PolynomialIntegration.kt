package group14.polynomial

import group14.REPLTest
import org.junit.Test

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
            REPLTest.replTest(input, expected)
        }
    }
}