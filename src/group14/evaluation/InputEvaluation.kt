package group14.evaluation

import group14.Option
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.parser.Parser
import group14.polynomial.Polynomial
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class InputEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        val evaluation = ArithmeticEvaluationCreator(tree, state).create()
        val result = evaluation?.evaluate()?.value() ?: run {
            output.println("")
            return
        }

        when (result) {
        // Special treatmet for polynomials.
            is Polynomial -> {
                if (Option.COEFFICIENT_LIST in state.options) {
                    output.println(result.toString())
                }
                else {
                    output.println(result.toPolynomialString())
                }
            }
        // Otherwise, just toString.
            else -> output.println(result.toString())
        }
    }
}