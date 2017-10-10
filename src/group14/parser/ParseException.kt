package group14.parser

/**
 * @author Ruben Schellekens
 */
open class ParseException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(message: String, source: Exception) : super(message, source)
}