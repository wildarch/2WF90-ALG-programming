package group14.evaluation

import group14.Option
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.field.AdditionTable
import group14.field.MultiplicationTable
import group14.field.OperationTable
import group14.parser.Parser
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class TableEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        if (tree.children.size < 2) {
            output.println("Usage: table <add|multiply> [q(X)] (mod p)")
            return
        }

        val evaluator = ArithmeticEvaluationCreator(tree, state).create() ?: return
        if (state.field == null) {
            output.println("No field was defined, expected (field [d(X)])")
            return
        }

        val tableType = tree.children[1].text.toLowerCase()
        val table = when (tableType) {
            "add" -> AdditionTable(state.field!!)
            "multiply" -> MultiplicationTable(state.field!!)
            else -> {
                output.println("Unkown table type $tableType. Supported types: add, multiply")
                return
            }
        }

        val style = if (Option.COEFFICIENT_LIST in state.options) {
            OperationTable.FormatStyle.COEFFICIENT_LIST
        }
        else {
            OperationTable.FormatStyle.PRETTY
        }
        output.println(table.format(2, style))
    }
}