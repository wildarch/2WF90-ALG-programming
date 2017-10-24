package group14.evaluation

import group14.parser.Parser
import java.io.PrintStream

/**
 * Prints the parse tree.
 *
 * @author Ruben Schellekens
 */
open class AliasesEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        output.println("      clearfield: clear, cf, cl")
        output.println("          euclid: gcd, euclids, euclidean")
        output.println("   isirreducible: irreducible, isirr")
        output.println("findirreducibles: irreducibles, findirr")
        output.println("        elements: elts")
        output.println("       parsetree: parse, tree, pt")
        output.println("         aliases: alias")
    }
}