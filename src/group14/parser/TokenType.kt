package group14.parser

import group14.evaluation.arithmetic.EvaluationObject
import group14.field.FiniteField
import group14.integer.ModularInteger
import group14.polynomial.Polynomial
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
                .map { it.objectInstance!! as TokenType }
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
    abstract class Operator(
            ordinal: Int,
            operator: String,
            val intFunction: (ModularInteger, ModularInteger) -> ModularInteger,
            val polyFunction: (Polynomial, Polynomial) -> Polynomial,
            val polyIntFunction: (Polynomial, ModularInteger) -> Polynomial,
            val intPolyFunction: (ModularInteger, Polynomial) -> Polynomial,
            val fieldPolyFunction: (FiniteField, Polynomial, Polynomial) -> Polynomial,
            val fieldPolyIntFunction: (FiniteField, Polynomial, ModularInteger) -> Polynomial,
            val fieldIntPolyFunction: (FiniteField, ModularInteger, Polynomial) -> Polynomial
    ) : TokenType(ordinal, operator), EvaluationObject

    abstract class UnaryOperator(
            ordinal: Int,
            operator: String,
            val integer: (ModularInteger) -> ModularInteger
    ) : Operator(
            ordinal,
            operator,
            { _, _ -> error("Unary operator doesn't support i,i->i functions.") },
            { _, _ -> error("Unary operator doesn't support p,p->p functions") },
            { _, _ -> error("Unary operator doesn't support p,i->p functions") },
            { _, _ -> error("Unary operator doesn't support p,i->p functions") },
            { _, _, _ -> error("Unary operator doesn't support f,p,p->p functions") },
            { _, _, _ -> error("Unary operator doesn't support f,p,i->p functions") },
            { _, _, _ -> error("Unary operator doesn't support f,p,i->p functions") }
    )

    abstract class ParseBlock : TokenType(-1, "")

    // Parse Blocks.
    object ROOT : ParseBlock()
    object MODULUS : ParseBlock()
    object FIELD : ParseBlock()
    object POLYNOMIAL : ParseBlock()
    object GROUP : ParseBlock()
    object META : ParseBlock()

    // Token Types.
    object WHITESPACE : TokenType(0, "[ \t]+")
    object INVERSE : UnaryOperator(1, "\\^-1", { it.inverse() })
    object NUMBER : TokenType(2, "((?<!\\d)-\\d+|\\d+)")
    object MODKEYWORD : TokenType(3, "mod")
    object FIELDKEYWORD : TokenType(4, "field")
    object DEFKEYWORD : TokenType(5, "def")
    object PARAMETER : TokenType(6, "[xX]")
    object KEYWORD : TokenType(7, "[a-z]+")
    object ADD : Operator(8, "\\+",
            { a, b -> a + b },
            { a, b -> a + b },
            { a, b -> a + b },
            { a, b -> b + a },
            { f, p, q -> f.add(p, q) },
            { f, p, b -> f.add(p, Polynomial(b)) },
            { f, a, q -> f.add(Polynomial(a), q) }

    )
    object SUBTRACT : Operator(9, "-",
            { a, b -> a - b },
            { a, b -> a - b },
            { a, b -> a - b },
            { i, p -> p.times(ModularInteger.reduce(-1, i.modulus)) + i },
            { f, p, q -> f.subtract(p, q) },
            { f, p, b -> f.subtract(p, Polynomial(b)) },
            { f, a, q -> f.subtract(Polynomial(a), q) }
    )
    object MULTIPLY : Operator(10, "\\*",
            { a, b -> a * b },
            { a, b -> a * b },
            { a, b -> a * b },
            { a, b -> b * a },
            { f, p, q -> f.multiply(p, q) },
            { f, p, b -> f.multiply(p, Polynomial(b)) },
            { f, a, q -> f.multiply(Polynomial(a), q) }
    )
    object POWER : Operator(11, "\\^",
            { _, _ -> error("Operation not supported.") },
            { _, _ -> error("Operation not supported.") },
            { _, _ -> error("Operation not supported.") },
            { _, _ -> error("Operation not supported.") },
            { _, _, _ -> error("Operation not supported.") },
            { _, _, _ -> error("Operation not supported.") },
            { _, _, _ -> error("Operation not supported.") }
    )
    object DIVIDE : Operator(12, "/",
            { a, b -> a / b },
            { a, b -> (a / b).first },
            { a, b -> a * b.inverse() },
            { a, b -> (Polynomial(a) / b).first },
            { f, p, q -> f.divide(p, q) },
            { f, p, b -> f.divide(p, Polynomial(b)) },
            { f, a, q -> f.divide(Polynomial(a), q) }
    )
    object REMAINDER : Operator(13, "%",
            { a, b -> ModularInteger.reduce(a.value % b.value, a.modulus) },
            { a, b -> (a / b).second },
            { a, b -> (a / Polynomial(arrayOf(b), a.modulus)).second },
            { a, b -> (Polynomial(a) / b).first },
            { f, _, _ -> error("Operation not supported for field $f.") },
            { f, _, _ -> error("Operation not supported for field $f.") },
            { f, _, _ -> error("Operation not supported for field $f.") }
    )
    object EQUALS : Operator(14, "=",
            { _, _ -> error("EQUALS not supported for i,i") },
            { _, _ -> error("EQUALS not supported for p,p") },
            { _, _ -> error("EQUALS not supported for p,i") },
            { _, _ -> error("EQUALS not supported for i,p") },
            { _, _, _ -> error("Unary operator doesn't support f,p,p->p functions") },
            { _, _, _ -> error("Unary operator doesn't support f,p,i->p functions") },
            { _, _, _ -> error("Unary operator doesn't support f,p,i->p functions") }
    )
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