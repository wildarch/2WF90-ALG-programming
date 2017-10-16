package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class DebugEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        try {
            output.println("Nothing to do here! :)")
        }
        catch (e: Exception) {
            output.println("Oh, oh! Something went wrong: ${e.message}")
            e.printStackTrace(output)
        }
    }
}