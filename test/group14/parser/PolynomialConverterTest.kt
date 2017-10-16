package group14.parser

import group14.Primes
import group14.integer.ModularInteger
import group14.polynomial.Polynomial
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals

/**
 * @author Ruben Schellekens
 */
open class PolynomialConverterTest {

    @Test
    fun `Coefficient List`() {
        val ran = Random(3103)
        val trim = Regex("(, 0)+]$")

        for (i in 1..25) {
            val modulus = Primes.random(bound = 50, random = ran)
            val list = ArrayList<Int>()
            val size = ran.nextInt(10) + 1
            val sign = if (ran.nextBoolean()) 1 else -1

            for (c in 1..size) {
                list.add(ran.nextInt(modulus * 2) * sign)
            }

            val reducedList = list
                    .map { ModularInteger.reduce(it.toLong(), modulus.toLong()).value }

            val input = "[${list.joinToString(", ")}]"
            val listOutput = "[${reducedList.joinToString(", ")}, 0]".replace(trim, "]")
            val output = "$listOutput (Z/${modulus}Z)"

            val lexer = Lexer(input)
            val parser = Parser(lexer)
            val tree = parser.constructParseTree()
            val polynomial = tree.children[0]

            val stringResult = Polynomial.fromNode(polynomial, modulus.toLong()).toString()
            assertEquals(output, stringResult, "Converting $input")
        }
    }

    @Test
    fun `Number`() {
        val ran = Random(3103)

        for (i in 1..25) {
            val modulus = Primes.random(bound = 50, random = ran).toLong()
            val number = ran.nextInt(modulus.toInt() * 5)
            val inputString = "[$number]"
            val value = ModularInteger.reduce(number.toLong(), modulus).value
            val valueString = if (value > 0) value.toString() else ""
            val expectedOutput = "[$valueString] (Z/${modulus}Z)"

            val polynomial = Parser(Lexer(inputString)).constructParseTree().children[0]
            val stringResult = Polynomial.fromNode(polynomial, modulus).toString()
            assertEquals(expectedOutput, stringResult, "Converting $inputString")
        }
    }

    @Test
    fun `Parameter Representation`() {
        // Input w/ modulus => Expected output in coefficient list representation.
        val testSet = mapOf(
                Pair("2-X^5-3X^4-2*X^4- 1x^6-1", 3) to "[1, 0, 0, 0, 1, 2, 2] (Z/3Z)",
                Pair("6x^2-48x- 32X^2-6x-36x^3+25x^5-48x", 29) to "[0, 14, 3, 22, 0, 25] (Z/29Z)",
                Pair("-7x^4 - 3*x^5+17x^5", 19) to "[0, 0, 0, 0, 12, 14] (Z/19Z)",
                Pair("82x ^3 - 76 x^4+56x-90X^2+89x^6-4X^5+26X", 47) to "[0, 35, 4, 35, 18, 43, 42] (Z/47Z)",
                Pair("49 x^5-45x^6+77 x ^6-50x^3+16X^5-48*x^4 + 15x^6", 43) to "[0, 0, 0, 36, 38, 22, 4] (Z/43Z)",
                Pair("12X^3", 7) to "[0, 0, 0, 5] (Z/7Z)",
                Pair("-22  *x ^6", 13) to "[0, 0, 0, 0, 0, 0, 4] (Z/13Z)",
                Pair("-8x^6+8x^3", 11) to "[0, 0, 0, 8, 0, 0, 3] (Z/11Z)",
                Pair("-34x^2+ 15x^5+35x-78X+7", 47) to "[7, 4, 13, 0, 0, 15] (Z/47Z)",
                Pair("21x^3+19x^2+5+10X^3-19", 11) to "[8, 0, 8, 9] (Z/11Z)",
                Pair("0", 13) to "[] (Z/13Z)",
                Pair("6+ 6- 6+6 - 6 - 6+6", 5) to "[1] (Z/5Z)"
        )

        var number = 0
        for ((pair, expected) in testSet.entries) {
            number++
            System.err.println("Test case #$number")
            val (inputPolynomial, modulus) = pair
            val polynomial = Parser(Lexer("[$inputPolynomial]")).constructParseTree().children[0]
            val stringResult = Polynomial.fromNode(polynomial, modulus.toLong()).toString()
            assertEquals(expected, stringResult, "#$number: Converting [$inputPolynomial]")
        }
    }
}