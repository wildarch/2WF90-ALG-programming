package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class ErrorEvaluation(val keyword: String) : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        output.println("Unkown command ‘$keyword’. Type ‘help’ for available commands.")
    }
}