package group14.evaluation

import group14.parser.Parser
import group14.parser.TreePrinter
import java.io.PrintStream

/**
 * Prints the parse tree.
 *
 * @author Ruben Schellekens
 */
open class ParsetreeEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        val printer = TreePrinter(tree, 4)
        output.println(printer.print().trim())
    }
}