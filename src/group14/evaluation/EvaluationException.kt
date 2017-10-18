package group14.evaluation

/**
 * @author Ruben Schellekens
 */
open class EvaluationException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, source: Exception) : super(message, source)
}

inline fun evaluationCheck(predicate: Boolean, message: () -> Any?) {
    if (!predicate) {
        throw EvaluationException(message()?.toString() ?: "null")
    }
}