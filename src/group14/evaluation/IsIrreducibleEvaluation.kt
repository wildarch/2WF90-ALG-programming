package group14.evaluation

import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.integer.ModularInteger
import group14.parser.Parser
import group14.parser.TokenType
import group14.polynomial.Polynomial
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class IsIrreducibleEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        if (tree.children.size < 2) {
            error("Usage: isirreducible [p(X)] (mod p)")
        }

        ArithmeticEvaluationCreator(tree, state).create()
        if (state.modulus == null) {
            output.println("No modulus was defined, expected (mod p)")
            return
        }

        val polynomialNode = tree.children[1]
        if (polynomialNode.type != TokenType.POLYNOMIAL && polynomialNode.type != TokenType.KEYWORD) {
            error("Expected a polynomial, got ${polynomialNode.text}")
        }

        val polynomial = if (polynomialNode.type == TokenType.KEYWORD) {
            val variable = polynomialNode.text
            val result = state.definitions[variable]?.value() ?: error("Variable '$variable' has not been defined")
            when (result) {
                is Boolean -> error("Result must not be a boolean.")
                is ModularInteger -> Polynomial(result)
                is Polynomial -> result
                else -> error("Could not convert variable '$variable' to a polynomial")
            }
        }
        else {
            Polynomial.fromNode(polynomialNode, state.modulus!!)
        }
        output.println(polynomial.isIrreducible())
    }
}