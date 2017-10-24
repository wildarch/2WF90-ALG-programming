package group14.field

import group14.Option
import group14.evaluation.REPL
import group14.parser.Lexer
import group14.parser.Parser
import group14.polynomial.Polynomial
import group14.replTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class FiniteFieldIntegration {
    @Test
    fun sum() {
        val tests = listOf(
                Pair("[X + 1] + [X + 1] (field [3X^2 + 1]) (mod 5)", "2X+2 (ℤ/5ℤ)"),
                Pair("[X^3 + X] + [X^2 + 1] (field [2X^4 + 2X^2 + 1]) (mod 3)", "X³+X²+X+1 (ℤ/3ℤ)")
        )
        replTest(tests)
    }

    @Test
    fun product() {
        val tests = listOf(
                Pair("[X + 1] * [X + 1] (field [3X^2 + 1]) (mod 5)", "2X+4 (ℤ/5ℤ)"),
                Pair("[X^3 + X] * [X^2 + 1] (field [X + 1]) (mod 3)", "EvaluationException: Could not evaluate [0, 1, 0, 1] (Z/3Z)\\*[1, 0, 1] (Z/3Z): Parameter [0, 1, 0, 1] (Z/3Z) is not an element of F3/(X+1 (ℤ/3ℤ))")
        )
        replTest(tests)
    }

    @Test
    fun quotient() {
        val tests = listOf(
                Pair("[X^2 + 2X + 1] / [X + 1] (mod 5)", "X+1 (ℤ/5ℤ)"),
                Pair("[X^5 + 2X^3 + X] / [X^2 + 1] (mod 3)", "X³+X (ℤ/3ℤ)")
        )
        replTest(tests)
    }

    @Test
    fun `Is irreducible`() {
        val tests = listOf(
                Pair("isirreducible [X^2 + 1] (mod 5)", "false"),
                Pair("isirreducible [X^2 + 1] (mod 2)", "false"),
                Pair("isirreducible [3X^2 + 1] (mod 5)", "true")
        )
        replTest(tests)
    }

    @Test
    fun `Find irreducibles`() {
        val baos = ByteArrayOutputStream()
        val output = PrintStream(baos)
        val input = ByteArrayInputStream("findirreducibles 3 of 4 (mod 3)".toByteArray())

        REPL(options= setOf(Option.SKIP_INTRO, Option.COEFFICIENT_LIST), input = input, output = output)
        val result = baos.toString()
        for (l in result.lines()) {
            val line = l.replace("(Z/3Z)", "(mod 3)")
            val p = Parser(Lexer(line))
            val tree = p.constructParseTree()
            if(tree.children.size == 0) {
                println("Skipping line: " + line)
                continue
            }
            val polyNode = tree.children[0]
            val poly = Polynomial.fromNode(polyNode, 3)
            assertTrue(poly.isIrreducible())
        }
    }
}