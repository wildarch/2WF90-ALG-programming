package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class ClearFieldEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        state.field = null
        output.println("Field cleared!")
    }
}