package group14.evaluation

import group14.parser.Parser
import group14.parser.TokenType
import java.io.PrintStream

/**
 * Executes actions based on a parse tree input.
 *
 * The evaluator will write its result to the given output stream whilst using the given state object to keep track
 * of the evaluation state (duh). This is useful, for example, when the [TokenType.PREVIOUS] token is used. The
 * previous result needs to be stored to be used later.
 *
 * [evaluate] will be called whenever the evaluator should evaluate a parse tree.
 *
 * @author Ruben Schellekens
 */
interface Evaluator {

    companion object {

        /**
         * Creates a new evaluator based on the given parse tree.
         */
        fun create(rootNode: Parser.ASTNode): Evaluator {
            // Regular evaluation.
            if (rootNode.children.isEmpty()) {
                return InputEvaluation()
            }

            val firstChild = rootNode.children[0]
            if (firstChild.type != TokenType.KEYWORD) {
                return InputEvaluation()
            }

            // Keyword
            return when (firstChild.text) {
                "table" -> TableEvaluation()
                "euclid", "gcd", "euclids", "euclidean" -> EuclidEvaluation()
                "isirreducible", "irreducible", "isirr" -> IsIrreducibleEvaluation()
                "findirreducibles", "irreducibles", "findirr" -> FindIrreduciblesEvaluation()
                "elements", "elts" -> ElementsEvaluation()
                "parsetree", "parse", "tree", "pt" -> ParsetreeEvaluation()
                "exit" -> ExitEvaluation()
                "help" -> HelpEvaluation()
                "debug" -> DebugEvaluation()
                else -> ErrorEvaluation(firstChild.text)
            }
        }
    }

    /**
     * Evaluates a sub tree given an initial evaluation state.
     *
     * @param tree
     *          The parse tree to evaluate.
     * @param output
     *          Where to write the output to.
     * @param state
     *          The initial state of the evaluation.
     * @throws EvaluationException When a problem occured during evaluation.
     */
    @Throws(EvaluationException::class)
    fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState)

    /**
     * Throws an [EvaluationException].
     */
    fun error(message: String): Nothing {
        throw EvaluationException(message)
    }
}