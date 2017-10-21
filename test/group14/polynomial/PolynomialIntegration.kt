package group14.polynomial

import group14.replTest
import org.junit.Test

class PolynomialIntegration {
    fun replTest(tests: List<Pair<String, String>>) {
        for ((input, expected) in tests) {
            replTest(input, expected)
        }
    }

    @Test
    fun sum() {
        val tests = listOf(
                Pair("[2X + 3X^2 + 1] + [X + 2X^2 + 1] (mod 5)", "3X+2 (ℤ/5ℤ)"),
                Pair("[X^10 - 5X^2 + 3X + 215] + [X^15 + 6X^2 + 1] (mod 7)", "X¹⁵+X¹⁰+X²+3X+6 (ℤ/7ℤ)")
        )
        replTest(tests)
    }

    @Test
    fun difference() {
        val tests = listOf(
                Pair("[X^2 + X + 2] - [X^2 + 2X -5] (mod 3)", "2X+1 (ℤ/3ℤ)"),
                Pair("[X^12 - 16X^234 + 808X] - [-12X^12 + 5X^12 + 123] (mod 13)", "10X²³⁴+8X¹²+2X+7 (ℤ/13ℤ)")
        )
        replTest(tests)
    }


    @Test
    fun product() {
        val tests = listOf(
                Pair("[X^2 + X + 2] * [X^2 + 2X -5] (mod 3)", "X⁴+2X²+2X+2 (ℤ/3ℤ)"),
                Pair("[X^12 - 16X^234 + 808X] * [-12X^12 + 5X^12 + 123] (mod 13)", "8X²⁴⁶+8X²³⁴+6X²⁴+12X¹³+6X¹²+12X (ℤ/13ℤ)")
        )
        replTest(tests)
    }

}