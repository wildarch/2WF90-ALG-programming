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
        // General idea is to scan for each precedence and combine the left operand, operator and
        // right operand to its calculated value.

        for (operators in precedence) {
            var i = 1
            while (i < elements.size) {
                var previous = elements[i - 1]
                val operator = elements[i]

                // Precedence.
                if (operator !in operators) {
                    i += 2
                    continue
                }

                // Manage nested evaluations.
                if (previous is ArithmeticEvaluation) {
                    val evaluationResult = previous.evaluate().value()
                    when (evaluationResult) {
                        is Boolean -> throw EvaluationException("Cannot have nested evaluations that evaluate to booleans")
                        is ModularInteger -> previous = evaluationResult
                        is Polynomial -> previous = evaluationResult
                    }
                }

                // Inverse
                if (operator == TokenType.INVERSE) {
                    elements.removeAt(i)
                    elements[i - 1] = if (previous is ModularInteger) {
                        try {
                            previous.inverse()
                        }
                        catch (exception: Exception) {
                            throw EvaluationException("Could not invert '$previous': ${exception.message}")
                        }
                    }
                    else if (previous is Polynomial) {
                        evaluationCheck(state.field != null) { "No field defined" }
                        evaluationCheck(state.field!!.modulus == previous.modulus) {
                            "Moduli of the polynomial and field do not match " +
                                    "(${state.field!!.modulus} vs ${(previous as Polynomial).modulus})"
                        }
                        state.field!!.inverse(previous)
                    }
                    else {
                        throw EvaluationException("Illegal object $previous")
                    }

                    continue
                }

                // Two sided
                evaluationCheck(i + 1 < elements.size) { "Operator $operator doesn't have a right hand side" }
                var next = elements[i + 1]

                // Manage nested evaluations.
                if (next is ArithmeticEvaluation) {
                    val evaluationResult = next.evaluate().value()
                    when (evaluationResult) {
                        is Boolean -> throw EvaluationException("Cannot have nested evaluations that evaluate to booleans")
                        is ModularInteger -> next = evaluationResult
                        is Polynomial -> next = evaluationResult
                    }
                }

                // Equality
                if (operator == TokenType.EQUALS) {
                    if (previous.javaClass != next.javaClass) {
                        if (previous is ModularInteger && next is Polynomial) {
                            val polynomial = Polynomial(previous)
                            val congruent = next.congruent(polynomial, state.polynomialModulus ?: throw EvaluationException("No polynomial modulus defined"))
                            return Right(congruent)
                        }
                        else if (next is ModularInteger && previous is Polynomial) {
                            val polynomial = Polynomial(next)
                            val congruent = previous.congruent(polynomial, state.polynomialModulus ?: throw EvaluationException("No polynomial modulus defined"))
                            return Right(congruent)
                        }

                        return Right(false)
                    }

                    return when (previous) {
                        is ModularInteger -> Right(previous == next)
                        is Polynomial -> {
                            val modulus = state.polynomialModulus ?: throw EvaluationException("No polynomial modulus defined")
                            Right(previous.congruent(next as Polynomial, modulus))
                        }
                        else -> throw EvaluationException("Equality not supported on ${next.javaClass.simpleName}")
                    }
                }

                // Other Operators
                val left = previous
                val right = next
                val operatorObject = operator as? TokenType.Operator ?: throw EvaluationException("$operator is not an operator")

                try {
                    // Calculate OUTSIDE field.
                    if (state.field == null) {
                        elements[i] = if (left is Polynomial && right is Polynomial) {
                            operatorObject.polyFunction(left, right)
                        }
                        else if (left is ModularInteger && right is ModularInteger) {
                            operatorObject.intFunction(left, right)
                        }
                        else if (left is Polynomial && right is ModularInteger) {
                            operator.polyIntFunction(left, right)
                        }
                        else if (left is ModularInteger && right is Polynomial) {
                            operator.intPolyFunction(left, right)
                        }
                        else {
                            error("Wrong evaluation case: Left=$left & right=$right")
                        }
                    }
                    // Calculate INSIDE field.
                    else {
                        val field = state.field!!
                        elements[i] = if (left is Polynomial && right is Polynomial) {
                            operatorObject.fieldPolyFunction(field, left, right)
                        }
                        else if (left is Polynomial && right is ModularInteger) {
                            operator.fieldPolyIntFunction(field, left, right)
                        }
                        else if (left is ModularInteger && right is Polynomial) {
                            operator.fieldIntPolyFunction(field, left, right)
                        }
                        else {
                            error("Wrong evaluation case: Left=$left & right=$right (field $field)")
                        }
                    }
                }
                catch (exception: Exception) {
                    throw EvaluationException("Could not evaluate $left${operatorObject.pattern}$right: " + exception.message, exception)
                }

                elements.removeAt(i + 1)
                elements.removeAt(i - 1)
            }
        }

        // Modulus field.
        var result = makeResult()
        val field = state.field
        val resultValue = result.value()
        if (field != null && resultValue is Polynomial) {
            if (!field.isElement(elements[0] as Polynomial)) {
                val (_, remainder) = resultValue / field.polynomial
                result = Middle(remainder)
            }
        }

        state.result = result
        return result
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