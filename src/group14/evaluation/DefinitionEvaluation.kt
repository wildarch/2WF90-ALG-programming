package group14.evaluation

import FormatStyle
import group14.Option
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.integer.ModularInteger
import group14.parser.Parser
import group14.parser.TokenType
import group14.polynomial.Polynomial
import java.io.PrintStream

/**
 * Prints the parse tree.
 *
 * @author Ruben Schellekens
 */
open class DefinitionEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        if (tree.children.size < 4) {
            output.println("Usage: def <name> = <value n|p(x)> (mod p)")
            return
        }

        // Scan modulus: always present, otherwise exception.
        try {
            ArithmeticEvaluationCreator(tree, state).create()
        }
        catch (e: EvaluationException) {
            output.println("EvaluationException: ${e.message}")
            if (Option.SHOW_STACKTRACE in state.options) {
                e.printStackTrace(output)
            }
            return
        }

        // Check correct ast nodes.
        val elements = tree.children
        check(elements[0].type == TokenType.DEFKEYWORD, "No def keyword found, got ${elements[0].type}")
        check(elements[1].type == TokenType.KEYWORD, "No variable name found, got ${elements[1].type}")
        check(elements[2].type == TokenType.EQUALS, "Equals operator expected, got ${elements[2].type}")
        when (elements[3].type) {
            is TokenType.POLYNOMIAL, is TokenType.NUMBER, is TokenType.PREVIOUS, TokenType.KEYWORD -> {
            }
            else -> throw EvaluationException("Number, polynomial or definition expected, got ${elements[3].type}")
        }

        // Evaluate value
        val subtree = tree.subTree(3, tree.children.size - 1)
        val evaluator = ArithmeticEvaluationCreator(subtree, state).create() ?: throw EvaluationException("Could not create evaluator")
        val result = evaluator.evaluate()
        val value = result.value()
        check(value !is Boolean, "Resulting value must not be a boolean")
        state.definitions.put(elements[1].text, result)

        // Print value.
        val styler = FormatStyle.fromOptions(state.options).styler
        when (value) {
            is Polynomial -> output.println(styler(value))
            is ModularInteger -> output.println(value.toString())
            else -> error("Unsupported result format: ${value?.javaClass?.simpleName}")
        }
    }

    private fun check(predicate: Boolean, message: String) {
        if (!predicate) {
            throw EvaluationException(message)
        }
    }
}