package group14.evaluation

import group14.parser.Parser
import group14.parser.TokenType
import java.io.PrintStream

/**
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

            }

            val firstChild = rootNode.children[0]
            if (firstChild.type != TokenType.KEYWORD) {

            }

            // Keyword
            @Suppress("WhenWithOnlyElse")
            return when (firstChild.text) {
                "parsetree" -> ParsetreeEvaluation()
                "exit" -> ExitEvaluation()
                "help" -> HelpEvaluation()
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
}