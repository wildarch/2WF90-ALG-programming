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
        private val values = TokenType::class.nestedClasses
                .filter { !it.isCompanion && !it.isAbstract }
                .map { it.objectInstance as TokenType }
                .sortedBy { it.ordinal }
                .toList()

        /**
         * The regex that is used to lex the input.
         */
        val LEXER_REGEX = Pattern.compile(buildString {
            for (type in values()) {
                append("|(?<${type.name}>${type.pattern})")
            }
        }.substring(1))!!

        /**
         * Lookup map that matches each token type's name to its object.
         */
        private val lookup: Map<String, TokenType> = {
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
    abstract class Operator(ordinal: Int, operator: String) : TokenType(ordinal, operator)
    abstract class UnaryOperator(ordinal: Int, operator: String) : Operator(ordinal, operator)

    // Enum elements.
    object WHITESPACE : Operator(0, "[ \t]+")
    object INVERSE : UnaryOperator(1, "\\^-1")
    object NUMBER : TokenType(2, "(?<!\\d)-\\d+|\\d+")
    object ADD : Operator(3, "\\+")
    object SUBTRACT : Operator(4, "-")
    object MULTIPLY : Operator(5, "\\*")
    object POWER : Operator(6, "\\^")
    object DIVIDE : Operator(7, "/")
    object REMAINDER : Operator(8, "%")
    object CONGRUENT : Operator(9, "=")
    object PARAMETER : Operator(10, "[xX]")
    object OPENBRACKET : TokenType(11, "\\[")
    object CLOSEBRACKET : TokenType(12, "]")
    object OPENPARENTHESIS : TokenType(13, "\\(")
    object CLOSEPARENTHESIS : TokenType(14, "\\)")
    object MODKEYWORD : TokenType(15, "mod")
    object SEPARATOR : TokenType(16, ",")

    /**
     * The pattern that must match
     */
    val pattern: Pattern

    /**
     * Get the name of the enum element.
     */
    val name = javaClass.simpleName!!

    /**
     * The ordinal of the enum element.
     */
    val ordinal: Int

    /**
     * The regex that matches the token.
     */
    constructor(ordinal: Int, pattern: String) {
        this.pattern = Pattern.compile(pattern)
        this.ordinal = ordinal
    }

    /**
     * @return [name]
     */
    override fun toString(): String {
        return name
    }
}