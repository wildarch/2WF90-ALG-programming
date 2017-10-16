package group14.evaluation

import group14.parser.Parser
import group14.parser.PolynomialConverter
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class DebugEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        try {
            val polynomial = tree.children[1]
            val converter = PolynomialConverter(polynomial, 17)
            val parts = converter.splitRepresentation()
            output.print("Parts[${parts.size}]: ")
            output.println(parts.joinToString(", "))
            output.println(converter.convert().toPolynomialString())
        }
        catch (e: Exception) {
            output.println("Oh, oh! Something went wrong: ${e.message}")
            e.printStackTrace(output)
        }
    }
}