package group14.evaluation

import FormatStyle
import group14.TaskState
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.launch
import group14.parser.Parser
import group14.parser.TokenType
import group14.polynomial.IrreducibleGenerator
import group14.polynomial.PolynomialStyler
import java.io.PrintStream

/**
 * @author Ruben Schellekens
 */
open class FindIrreduciblesEvaluation : Evaluator {

    companion object {

        /**
         * Contains for each degree from what modulus there must be a performance warning.
         *
         * If a degree is not present, it is always for all moduli.
         */
        private val performanceMatrix = mapOf(
                1 to Integer.MAX_VALUE,
                2 to 47,
                3 to 11,
                4 to 7,
                5 to 5,
                6 to 5,
                7 to 3,
                8 to 3,
                9 to 3
        )
    }

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        if (tree.children.size < 4) {
            error("Usage: findirreducibles n ofdegree d (mod p)")
        }

        ArithmeticEvaluationCreator(tree, state).create()
        if (state.modulus == null) {
            output.println("No modulus defined, expected (mod p)")
            return
        }

        val style = FormatStyle.fromOptions(state.options)

        // Parse arguments.
        val amountNode = tree.children[1]
        val degreeNode = tree.children[3]

        if (amountNode.type != TokenType.NUMBER) {
            error("Amount must be a number, got ${amountNode.text}")
        }
        if (degreeNode.type != TokenType.NUMBER) {
            error("Degree must be a number, got ${degreeNode.text}")
        }

        val amount = amountNode.text.toInt()
        val degree = degreeNode.text.toInt()

        if (amount < 1) {
            error("Amount must be positive, got $amount")
        }
        if (degree < 1) {
            error("Degree must be positive, got $degree")
        }

        // Give warning for performance.
        val maxMod = performanceMatrix[degree]
        if (maxMod == null || state.modulus!! >= maxMod) {
            System.err.println("Irreducible generation might take a long time for degree $degree in Z/${state.modulus}Z")
        }

        // Generate polynomials.
        findIrreducibles(amount, degree, state.modulus!!, output, style)
    }

    private fun findIrreducibles(amount: Int, degree: Int, modulus: Long, output: PrintStream, styler: PolynomialStyler) {
        // Split up the generation in parts.
        val generator = IrreducibleGenerator(modulus, degree)

        val tasks = ArrayList<TaskState<Unit>>()
        for (i in 1..amount) {
            tasks.add(launch {
                output.println(styler.styler(generator.generate()))
            })
        }

        tasks.forEach { it.wait() }
    }
}