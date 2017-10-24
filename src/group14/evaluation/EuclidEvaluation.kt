package group14.evaluation

import group14.Option
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.field.OperationTable
import group14.integer.IntegerEuclids
import group14.parser.Parser
import group14.parser.TokenType
import group14.polynomial.Polynomial
import group14.polynomial.PolynomialEuclids
import java.io.PrintStream

/**
 * Prints the parse tree.
 *
 * @author Ruben Schellekens
 */
open class EuclidEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        if (tree.children.size < 4) {
            error("Usage: euclid [p(X)] and [q(X)] (mod p)")
        }

        // Validate syntax.
        val pNode = tree.children[1]
        val andNode = tree.children[2]
        val qNode = tree.children[3]

        if (andNode.type != TokenType.KEYWORD || andNode.text != "and") {
            error("Missing 'and' keyword")
        }

        // Check polynomial.
        if (pNode.type == TokenType.POLYNOMIAL && qNode.type == TokenType.POLYNOMIAL) {
            val a = Polynomial.fromNode(pNode, state.modulus!!)
            val b = Polynomial.fromNode(qNode, state.modulus!!)
            polynomialEuclid(tree, output, state, a, b)
            return
        }
        else if (pNode.type != TokenType.POLYNOMIAL && qNode.type == TokenType.POLYNOMIAL) {
            error("p(x) must be a polynomial, got $pNode")
        }
        else if (qNode.type != TokenType.POLYNOMIAL && pNode.type == TokenType.POLYNOMIAL) {
            error("q(x) must be a polynomial, got $qNode")
        }

        // Check integers.
        if (pNode.type == TokenType.NUMBER && qNode.type == TokenType.NUMBER) {
            val a = pNode.text.toLong()
            val b = qNode.text.toLong()
            integerEuclid(output, state, a, b)
            return
        }
        else if (pNode.type == TokenType.NUMBER && qNode.type != TokenType.NUMBER) {
            error("q must be a number, got $qNode")
        }
        else if (qNode.type == TokenType.NUMBER && pNode.type == TokenType.NUMBER) {
            error("p must be a number, got $pNode")
        }

        error("Illegal arguments p/q: ${pNode.text}/${qNode.text}. Expected polynomials or integers.")
    }

    private fun integerEuclid(output: PrintStream, state: EvaluationState, a: Long, b: Long) {
        // Execute euclids.
        val euclids = try {
            IntegerEuclids(a, b)
        }
        catch (exception: Exception) {
            error("Illegal euclid input: ${exception.message}")
        }

        val (x, y, gcd) = euclids.execute()
        output.println("gcd($a, $b) = $gcd = $x($a) + $y($b)")
    }

    private fun polynomialEuclid(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState,
                                 a: Polynomial, b: Polynomial) {
        // Validate state
        ArithmeticEvaluationCreator(tree, state).create()
        if (state.modulus == null) {
            error("No coefficient modulus defined")
        }

        // Execute euclids.
        val euclids = try {
            PolynomialEuclids(a, b)
        }
        catch (exception: Exception) {
            error("Illegal euclid input: ${exception.message}")
        }

        val (x, y, gcd) = euclids.execute()
        val styler = if (Option.COEFFICIENT_LIST in state.options) {
            FormatStyle.COEFFICIENT_LIST
        }
        else {
            FormatStyle.PRETTY
        }.styler

        // Print result
        val stringA = styler(a)
        val stringB = styler(b)
        val stringX = styler(x)
        val stringY = styler(y)
        val stringGCD = styler(gcd)
        output.println("gcd($stringA, $stringB) = $stringGCD = ($stringX)($stringA) + ($stringY)($stringB)")
    }
}