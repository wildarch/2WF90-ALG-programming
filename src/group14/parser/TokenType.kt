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
                .filter { it.ordinal >= 0 }
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
    abstract class ParseBlock : TokenType(-1, "")

    // Parse Blocks.
    object ROOT : ParseBlock()
    object MODULUS : ParseBlock()
    object FIELD : ParseBlock()
    object POLYNOMIAL : ParseBlock()
    object GROUP : ParseBlock()
    object META: ParseBlock()

    // Token Types.
    object WHITESPACE : Operator(0, "[ \t]+")
    object INVERSE : UnaryOperator(1, "\\^-1")
    object NUMBER : TokenType(2, "((?<!\\d)-\\d+|\\d+)")
    object MODKEYWORD : TokenType(3, "mod")
    object FIELDKEYWORD: TokenType(4, "field")
    object DEFKEYWORD: TokenType(5, "def")
    object KEYWORD : TokenType(6, "[a-z][a-z]+")
    object ADD : Operator(7, "\\+")
    object SUBTRACT : Operator(8, "-")
    object MULTIPLY : Operator(9, "\\*")
    object POWER : Operator(10, "\\^")
    object DIVIDE : Operator(11, "/")
    object REMAINDER : Operator(12, "%")
    object EQUALS : Operator(13, "=")
    object PARAMETER : TokenType(14, "[xX]")
    object PREVIOUS : TokenType(15, "_")
    object OPENBRACKET : TokenType(16, "\\[")
    object CLOSEBRACKET : TokenType(17, "]")
    object OPENPARENTHESIS : TokenType(18, "\\(")
    object CLOSEPARENTHESIS : TokenType(19, "\\)")
    object OPENBRACE : TokenType(20, "\\{")
    object CLOSEBRACE : TokenType(21, "}")
    object SEPARATOR : TokenType(22, ",")

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