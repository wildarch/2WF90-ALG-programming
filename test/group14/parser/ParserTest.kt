package group14.parser

import group14.parser.TokenType.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * @author Ruben Schellekens
 */
open class ParserTest {

    @Test
    fun `Parse tree`() {
        // Empty tree.
        val empty = Parser(Lexer(""))
        val emptyRoot = empty.constructParseTree()
        assertTrue(emptyRoot.isRoot, "Root check empty.")
        assertTrue(emptyRoot.children.isEmpty(), "Root has no children from empty input.")
        assertNull(emptyRoot.next, "Root has no previous sibling.")
        assertNull(emptyRoot.previous, "Root has no previous sibling.")

        // Fancy tree.
        println("[3, 6, 7] * -5 (mod 181) = [3+16*X - 7X^2] (mod [181+3X^3-16*X^9])")
        val parser = Parser(Lexer("[3, 6, 7] * -5 (mod 181) = [3+16*X - 7X^2] (mod [181+3X^3-16*X^9])"))
        val rootNode = parser.constructParseTree()
        println(rootNode.children)

        // check root.
        assertEquals(7, rootNode.children.size, "7 children")
        assertNull(rootNode.next, "Root has no next sibling.")
        assertNull(rootNode.previous, "Root has no previous sibling.")

        // Check structure.
        val children = rootNode.children
        val polynomialList = children[0]
        val multiply = children[1]
        val minusFive = children[2]
        val modulus = children[3]
        val congruence = children[4]
        val polynomial = children[5]
        val modulusPolynomial = children[6]

        // Types.
        assertEquals(POLYNOMIAL, polynomialList.type)
        assertEquals(MULTIPLY, multiply.type)
        assertEquals(NUMBER, minusFive.type)
        assertEquals(MODULUS, modulus.type)
        assertEquals(CONGRUENT, congruence.type)
        assertEquals(POLYNOMIAL, polynomial.type)
        assertEquals(MODULUS, modulusPolynomial.type)

        // Children size.
        assertEquals(6, polynomialList.children.size)
        assertEquals(0, multiply.children.size)
        assertEquals(0, minusFive.children.size)
        assertEquals(3, modulus.children.size)
        assertEquals(0, congruence.children.size)
        assertEquals(11, polynomial.children.size)
        assertEquals(3, modulusPolynomial.children.size)

        // Text leaf contents.
        assertEquals("3", polynomialList.children[0].text)
        assertEquals(",", polynomialList.children[1].text)
        assertEquals("6", polynomialList.children[2].text)
        assertEquals(",", polynomialList.children[3].text)
        assertEquals("7", polynomialList.children[4].text)
        assertEquals("*", multiply.text)
        assertEquals("-5", minusFive.text)
        assertEquals("mod", modulus.children[0].text)
        assertEquals("181", modulus.children[1].text)
        assertEquals("=", congruence.text)
        assertEquals("3", polynomial.children[0].text)
        assertEquals("+", polynomial.children[1].text)
        assertEquals("16", polynomial.children[2].text)
        assertEquals("*", polynomial.children[3].text)
        assertEquals("X", polynomial.children[4].text)
        assertEquals("-", polynomial.children[5].text)
        assertEquals("7", polynomial.children[6].text)
        assertEquals("X", polynomial.children[7].text)
        assertEquals("^", polynomial.children[8].text)
        assertEquals("2", polynomial.children[9].text)
        assertEquals("mod", modulusPolynomial.children[0].text)
        assertEquals("[181+3X^3-16*X^9]", modulusPolynomial.children[1].text)

        // Next
        for (i in 0..3) {
            assertEquals(polynomialList.children[i + 1], polynomialList.children[i].next)
        }
        for (i in 0..8) {
            assertEquals(polynomial.children[i + 1], polynomial.children[i].next)
        }
        for (i in 0..10) {
            assertEquals(modulusPolynomial.children[1].children[i + 1], modulusPolynomial.children[1].children[i].next)
        }

        assertEquals(multiply, polynomialList.next)
        assertEquals(minusFive, multiply.next)
        assertEquals(modulus, minusFive.next)
        assertEquals(congruence, modulus.next)
        assertEquals(polynomial, congruence.next)
        assertEquals(modulusPolynomial, polynomial.next)
        assertEquals(null, modulusPolynomial.next)

        // Previous
        for (i in 1..4) {
            assertEquals(polynomialList.children[i - 1], polynomialList.children[i].previous)
        }
        for (i in 1..9) {
            assertEquals(polynomial.children[i - 1], polynomial.children[i].previous)
        }
        for (i in 1..11) {
            assertEquals(modulusPolynomial.children[1].children[i - 1], modulusPolynomial.children[1].children[i].previous)
        }

        assertEquals(null, polynomialList.previous)
        assertEquals(polynomialList, multiply.previous)
        assertEquals(multiply, minusFive.previous)
        assertEquals(minusFive, modulus.previous)
        assertEquals(modulus, congruence.previous)
        assertEquals(congruence, polynomial.previous)
        assertEquals(polynomial, modulusPolynomial.previous)

        // Parent
        assertEquals(rootNode, polynomialList.parent)
        assertEquals(rootNode, multiply.parent)
        assertEquals(rootNode, minusFive.parent)
        assertEquals(rootNode, modulus.parent)
        assertEquals(rootNode, congruence.parent)
        assertEquals(rootNode, polynomial.parent)
        assertEquals(rootNode, modulusPolynomial.parent)

        for (node in rootNode.allChildren()) {
            checkParent(node)
        }
    }

    private fun checkParent(node: Parser.ASTNode) {
        for (child in node.children) {
            assertEquals(node, child.parent)
        }
    }

    @Test
    fun `Parse exceptions`() {
        // Invalid input.
        val failingTests = listOf(
                "3+[",
                "[X] + 4 mod",
                "[3, , 6]",
                "[X^2+6 3]",
                "[4X 9]",
                "16 [X]",
                "7 3 8 (mod 8)",
                "[3, 6x^2]",
                "[4X +6,2]",
                "4 +* 16",
                "8^-1^-1",
                "[3,2,-,-4]",
                "6 (mod 19) + 14 (mod 11)",
                "()",
                "19 + [X] (4)",
                "7 (mod mod)",
                "[18] (mod X)",
                "[X + 16 % 4]",
                "[34X] = 34 (mod 17) = 23"
        )

        for (test in failingTests) {
            val parser = Parser(Lexer(test))
            assertFailsWith(ParseException::class, "Parse fail <$test>") {
                parser.constructParseTree()
            }
        }

        // Valid input.
        val nonFailingTests = listOf(
                "3^-1*5+[x]",
                "6 (mod 19) + 14(mod[X])"
        )

        for (test in nonFailingTests) {
            val parser = Parser(Lexer(test))
            parser.constructParseTree()
        }
    }

    @Test
    fun `All nodes`() {
        // Empty.
        assertEquals(0, Parser(Lexer("")).constructParseTree().allChildren().size)

        // Simple parse thing.
        val input = "[3, 4] + [X^2] (mod 3)"
        assertEquals(15, Parser(Lexer(input)).constructParseTree().allChildren().size)
    }
}