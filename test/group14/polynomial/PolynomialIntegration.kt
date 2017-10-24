package group14.polynomial

import group14.replTest
import org.junit.Test

class PolynomialIntegration {

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

    @Test
    fun `scalar multiple`() {
        val tests = listOf(
                Pair("2 * [X^2 + X + 2] (mod 2)", "0 (ℤ/2ℤ)"),
                Pair("12345 * [12X^3 + 123451X^1624 + X + 987] (mod 131)", "78X¹⁶²⁴+110X³+31X+74 (ℤ/131ℤ)")
        )
        replTest(tests)
    }

    @Test
    fun division() {
        val tests = listOf(
                // Quotient
                Pair("[76X^75 + 975X^4 + 4X^87] / [-432X^23 + X^8] (mod 7)", "2X⁶⁴+3X⁵²+6X⁴⁹+2X³⁷+4X³⁴+6X²²+5X¹⁹+4X⁷+X⁴ (ℤ/7ℤ)"),
                Pair("[6X^5 + 6X^3 + 9X^2 + 6X] / [9X^6 + 16X^3 + 3] (mod 17)", "0 (ℤ/17ℤ)"),
                // Remainder
                Pair("[76X^75 + 975X^4 + 4X^87] % [-432X^23 + X^8] (mod 7)", "3X¹⁵+6X¹²+2X⁴ (ℤ/7ℤ)"),
                Pair("[6X^5 + 6X^3 + 9X^2 + 6X] % [9X^6 + 16X^3 + 3] (mod 17)", "6X⁵+6X³+9X²+6X (ℤ/17ℤ)")

        )
        replTest(tests)
    }

    @Test
    fun `Extended euclidian algorithm`() {
        val tests = listOf(
                Pair("euclid [76X^75 + 975X^4 + 4X^87] and [-432X^23 + X^8] (mod 7)", "gcd(4X⁸⁷+6X⁷⁵+2X⁴ (ℤ/7ℤ), 2X²³+X⁸ (ℤ/7ℤ)) = 3X⁴ (ℤ/7ℤ) = (5X¹⁸+6X¹⁷+4X¹⁶+5X¹⁵+6X¹⁴+5X¹³+6X¹²+2X¹¹+3X¹⁰+X⁹+4X⁸+X⁶+X⁵+6X⁴+5 (ℤ/7ℤ))(4X⁸⁷+6X⁷⁵+2X⁴ (ℤ/7ℤ)) + (4X⁸²+2X⁸¹+6X⁸⁰+4X⁷⁹+2X⁷⁸+4X⁷⁷+2X⁷⁶+3X⁷⁵+X⁷⁴+5X⁷³+6X⁷²+4X⁷⁰+X⁶⁹+4X⁶⁸+4X⁶⁷+2X⁶⁶+3X⁶⁵+5X⁶⁴+3X⁶²+3X⁶¹+4X⁶⁰+3X⁵⁹+5X⁵⁸+X⁵⁷+3X⁵⁶+5X⁵⁵+3X⁵⁴+5X⁵³+4X⁵²+6X⁵¹+2X⁵⁰+X⁴⁹+2X⁴⁷+2X⁴⁶+5X⁴⁵+2X⁴⁴+X⁴³+3X⁴²+2X⁴¹+X⁴⁰+2X³⁹+X³⁸+5X³⁷+4X³⁶+6X³⁵+3X³⁴+6X³²+6X³¹+X³⁰+6X²⁹+3X²⁸+2X²⁷+6X²⁶+3X²⁵+6X²⁴+3X²³+X²²+5X²¹+4X²⁰+2X¹⁹+4X¹⁷+4X¹⁶+3X¹⁵+4X¹⁴+2X¹³+6X¹²+4X¹¹+2X¹⁰+4X⁹+2X⁸+3X⁷+X⁶+5X⁵+6X⁴+5X²+5X+2 (ℤ/7ℤ))(2X²³+X⁸ (ℤ/7ℤ))")
        )
        replTest(tests)
    }


    @Test
    fun `Congruence`() {
        val tests = listOf(
                Pair("[X + 1] = [2X + 2] (mod [X + 1]) (mod 5)", "true"),
                Pair("[X + 1] = [2X + 2] (mod 5) (mod [X + 1])", "true"),
                Pair("[21X^5 + 10X^3 + 14X] = [X^3 - X] (mod [X^2 + X + 1]) (mod 3)", "true"),
                Pair("[21X^5 + 10X^3 + 14X] = [X^3 - X] (mod [X^2 + X + 1]) (mod 5)", "false")
        )
        replTest(tests)
    }
}