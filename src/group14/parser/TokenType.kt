package group14.parser

import java.util.regex.Pattern

/**
 * @author Ruben Schellekens
 */
sealed class TokenType {

    companion object {

        /**
         * The regex that is used to lex the input.
         */
        val LEXER_REGEX = Pattern.compile(buildString {
            for (type in values()) {
                append(String.format("|(?<%s>%s)", type.name, type.pattern))
            }
        }.substring(1))!!

        /**
         * List of all token types.
         */
        private val values = listOf(
                ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, INVERSE, CONGRUENT,
                OPEN_BRACKET, CLOSE_BRACKET, NUMBER
        )

        /**
         * Get all token types.
         */
        @JvmStatic
        fun values() = values
    }

    // Subtypes
    abstract class Operator(val operator: String) : TokenType(operator)

    // Enum elements.
    object ADD : Operator("\\+")
    object SUBTRACT : Operator("-")
    object MULTIPLY : Operator("\\*")
    object DIVIDE : Operator("/")
    object REMAINDER : Operator("%")
    object INVERSE : Operator("\\^-1")
    object CONGRUENT : Operator("=")
    object OPEN_BRACKET : TokenType("\\[")
    object CLOSE_BRACKET : TokenType("]")
    object NUMBER : TokenType("-?\\d+")

    /**
     * The pattern that must match
     */
    val pattern: Pattern

    /**
     * Get the name of the enum element.
     */
    val name = javaClass.simpleName!!

    /**
     * The regex that matches the token.
     */
    constructor(pattern: String) {
        this.pattern = Pattern.compile(pattern)
    }

    /**
     * @return [name]
     */
    override fun toString(): String {
        return name
    }
}