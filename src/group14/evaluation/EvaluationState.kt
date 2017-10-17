package group14.evaluation

import group14.evaluation.arithmetic.Result
import group14.field.FiniteField
import group14.polynomial.Polynomial

/**
 * @author Ruben Schellekens
 */
data class EvaluationState(
        var modulus: Long? = null,
        var polynomialModulus: Polynomial? = null,
        var field: FiniteField? = null,
        var result: Result? = null
)