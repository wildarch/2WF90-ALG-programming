package group14.parser

import java.util.regex.Pattern

/**
 * @author Ruben Schellekens
 */
sealed class TokenType {

    companion object {

        /**
         * List of all token types.
         */
        private val values = listOf(
                WHITESPACE, INVERSE, NUMBER, ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, CONGRUENT,
                OPENBRACKET, CLOSEBRACKET, SEPARATOR
        )

        /**
         * The regex that is used to lex the input.
         */
        val LEXER_REGEX = Pattern.compile(buildString {
            for (type in values()) {
                append(String.format("|(?<%s>%s)", type.name, type.pattern))
            }
        }.substring(1))!!

        /**
         * Lookup map that matches each token type's name to its object.
         */
        private val lookup = {
            val map = HashMap<String, TokenType>()
            values().forEach {
                map.put(it.name, it)
            }
            map
        }()

        /**
         * Get all token types.
         */
        @JvmStatic
        fun values() = values

        /**
         * Finds the token type object with the given name.
         */
        @JvmStatic
        operator fun get(name: String) = lookup[name]
    }

    // Subtypes
    abstract class Operator(val operator: String) : TokenType(operator)

    // Enum elements.
    object WHITESPACE : Operator("\\s+")
    object INVERSE : Operator("\\^-1")
    object NUMBER : TokenType("-?\\d+")
    object ADD : Operator("\\+")
    object SUBTRACT : Operator("-")
    object MULTIPLY : Operator("\\*")
    object DIVIDE : Operator("/")
    object REMAINDER : Operator("%")
    object CONGRUENT : Operator("=")
    object OPENBRACKET : TokenType("\\[")
    object CLOSEBRACKET : TokenType("]")
    object SEPARATOR : TokenType(",")

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