package group14.evaluation.arithmetic

import group14.Either
import group14.Left
import group14.Middle
import group14.Right
import group14.evaluation.EvaluationException
import group14.evaluation.EvaluationState
import group14.evaluation.evaluationCheck
import group14.integer.ModularInteger
import group14.parser.TokenType
import group14.polynomial.Polynomial

typealias Result = Either<ModularInteger, Polynomial, Boolean>

/**
 * @author Ruben Schellekens
 */
open class ArithmeticEvaluation(val state: EvaluationState, val elements: MutableList<EvaluationObject>) : EvaluationObject {

    companion object {

        /**
         * Precedence of the operators.
         *
         * The lowest index of the array has the highest precedence.
         */
        private val precedence = arrayOf(
                setOf(TokenType.INVERSE, TokenType.POWER),
                setOf(TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.REMAINDER),
                setOf(TokenType.ADD, TokenType.SUBTRACT),
                setOf(TokenType.EQUALS)
        )
    }

    /**
     * Evaluates the evaluation.
     *
     * @return A [Result]: The resulting ModularInteger, Polynomial or Boolean.
     */
    fun evaluate(): Result {
        for (operators in precedence) {
            var i = 1
            while (i < elements.size - 1) {
                val previous = elements[i - 1]
                val operator = elements[i]

                // Inverse
                if (operator == TokenType.INVERSE) {
                    elements.removeAt(i)
                    elements[i - 1] = if (previous is ModularInteger) {
                        previous.inverse()
                    }
                    else if (previous is Polynomial) {
                        evaluationCheck(state.field != null) { "No field defined" }
                        // TODO: Polynomial Inverse
                        previous
                    }
                    else {
                        throw EvaluationException("Illegal object $previous")
                    }

                    continue
                }

                // Two sided
                val next = elements[i + 1]

                // Equality
                if (operator == TokenType.EQUALS) {
                    if (previous.javaClass != next.javaClass) {
                        return Right(false)
                    }

                    when (previous) {
                        is ModularInteger -> return Right(previous == next)
                        is Polynomial -> {
                            val modulus = state.polynomialModulus ?: throw EvaluationException("No polynomial modulus defined")
                            return Right(previous.congruent(next as Polynomial, modulus))
                        }
                    }
                }
            }
        }

        return makeResult()
    }

    /**
     * Puts the last remaining [EvaluationObject] into a [Result].
     */
    private fun makeResult(): Result = when (elements[0]) {
        is ModularInteger -> Left(elements[0] as ModularInteger)
        is Polynomial -> Middle(elements[0] as Polynomial)
        else -> Right(elements[0] == True)
    }

    /**
     * Used to represent `true`, must be used with `True?` where `null` is a `false` state.
     *
     * @author Ruben Schellekens
     */
    private object True : EvaluationObject
}