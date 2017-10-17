package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * Exits the program.
 *
 * @author Ruben Schellekens
 */
open class ExitEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        output.println("Goodbye. Have a nice day!")
        System.exit(1)
    }
}