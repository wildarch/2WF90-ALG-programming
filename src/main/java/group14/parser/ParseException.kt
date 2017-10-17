package group14.parser

/**
 * @author Ruben Schellekens
 */
open class ParseException : RuntimeException {

    /**
     * The `column`th character where the exception was raised.
     */
    val column: Int

    constructor(message: String, column: Int) : super(message) {
        this.column = column
    }

    constructor(message: String, column: Int, source: Exception) : super(message, source) {
        this.column = column
    }
}