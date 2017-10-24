package group14.evaluation

import FormatStyle
import group14.Primes
import group14.evaluation.arithmetic.ArithmeticEvaluationCreator
import group14.integer.ModularInteger
import group14.parser.Lexer
import group14.parser.Parser
import group14.polynomial.Polynomial
import java.io.PrintStream
import java.util.*

/**
 * @author Ruben Schellekens
 */
open class DebugEvaluation : Evaluator {

    override fun evaluate(tree: Parser.ASTNode, output: PrintStream, state: EvaluationState) {
        try {
            if (tree.children.size > 1) {
                if (tree.children[1].text == "qa") {
                    qualityAssurance(output, state)
                    return
                }
            }

            output.println("Nothing to do here! :)")
        }
        catch (e: Exception) {
            output.println("Oh, oh! Something went wrong: ${e.message}")
            e.printStackTrace(output)
        }
    }

    private fun qualityAssurance(output: PrintStream, state: EvaluationState) {
        output.println("You will be prompted by several polynomials.")
        output.println("Please enter an expression that evaluates to the value of the given polynomial.")

        val random = Random()
        while (true) {
            val degree = random.nextInt(8)
            val modulus = Primes.random(25, random).toLong()
            var polynomial = Polynomial.random(modulus.toLong(), degree, random)

            // Remove random things.
            val coefficients = polynomial.coefficients
            for (i in 0..polynomial.degree) {
                if (random.nextInt(3) == 0) {
                    coefficients[i] = ModularInteger(0, modulus)
                }
            }
            polynomial = Polynomial(coefficients, modulus)

            output.println("\nPlease enter: ${polynomial.toPolynomialString()}")
            output.print("QA> ")

            val input: String = readLine() ?: ""
            val tree = Parser(Lexer(input)).constructParseTree()

            try {
                val evaluator = ArithmeticEvaluationCreator(tree, state).create()
                val result = evaluator?.evaluate()?.value() ?: output.println("Evaluator evaluates to 'null'")
                if (result !is Polynomial) {
                    output.println("Input '$input' does not evaluate to a POLYNOMIAL!")
                    continue
                }

                if (result != polynomial) {
                    val styler = FormatStyle.fromOptions(state.options).styler
                    output.println("Input '$input' does not evaluate to '${styler(polynomial)}'!")
                    continue
                }
            }
            catch (e: Exception) {
                e.printStackTrace(output)
            }
        }
    }
}