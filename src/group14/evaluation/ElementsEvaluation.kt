package group14.evaluation

import group14.Option
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.field.OperationTable
import group14.parser.Parser
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class ElementsEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        ArithmeticEvaluationCreator(tree, state).create()
        if (state.field == null) {
            output.println("No field was defined, expected (field [d(X)])")
            return
        }

        val style = if (Option.COEFFICIENT_LIST in state.options) {
            FormatStyle.COEFFICIENT_LIST
        }
        else {
            FormatStyle.PRETTY
        }

        state.field!!.getElements().toList()
                .sorted()
                .forEach {
                    output.println(style.styler(it))
                }
    }
}