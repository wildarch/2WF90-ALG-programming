package group14.parser

import group14.parser.TokenType.WHITESPACE

/**
 * @author Ruben Schellekens
 */
open class Lexer(input: String) {

    /**
     * The current matcher lexing using the regex.
     */
    private val matcher = TokenType.LEXER_REGEX.matcher(input)

    /**
     * List containing all the tokens that have been consumed.
     */
    private val tokens = ArrayList<Token>()

    /**
     * The starting column of the current token.
     */
    private var column: Int = 0

    /**
     * Contains all the whitespace from the last processed whitespace token.
     *
     * If the previous token was not whitespace, it will be an empty string.
     */
    var whitespaceBuffer: String = ""
        private set(value) {
            field = value
        }

    /**
     * Finds the next token and advances, returns the consumed token.
     *
     * @return The consumed token, or `null` when the end has been reached.
     */
    fun next(): Token? {
        if (!matcher.find()) {
            return null
        }

        for (type in TokenType.values()) {
            // Consume text.
            val name = type.name
            val group = matcher.group(name) ?: continue
            val token = Token(type, group)
            tokens.add(token)
            column += group.length

            // Update whitespace buffer.
            if (token.type == WHITESPACE) {
                whitespaceBuffer = group
            }
            else if (lexedTokens() > 1 && lookBehind()?.type != WHITESPACE) {
                whitespaceBuffer = ""
            }

            return token
        }

        throw AssertionError("Should not happen!")
    }

    /**
     * [next], but then ignoring whitespace characters.
     */
    fun nextIgnoreWhitespace(): Token? {
        var next = next()
        while (next?.type == WHITESPACE) {
            next = next()
        }
        return next
    }

    /**
     * Get the current token without advancing.
     */
    fun current() = lookBehind(0)!!

    /**
     * Looks `distance` tokens behind.
     *
     * @throws ArrayIndexOutOfBoundsException When there is no token available.
     */
    @Throws(ArrayIndexOutOfBoundsException::class)
    fun lookBehind(distance: Int = 1): Token? {
        val index = tokens.size - 1 - distance
        if (index !in 0 until tokens.size) {
            return null
        }
        return tokens[index]
    }

    /**
     * Looks `distance` tokens behind and if it found whitespace, it will look one further back.
     *
     * @throws ArrayIndexOutOfBoundsException When there is no token available.
     */
    @Throws(ArrayIndexOutOfBoundsException::class)
    fun lookBehindNoWhitespace(distance: Int = 1): Token? {
        var behind = lookBehind(distance)
        var i = 1
        while (behind?.type == WHITESPACE) {
            behind = lookBehind(distance + i)
            i++
        }
        return behind
    }

    /**
     * Gets how many tokens have been consumed.
     */
    fun lexedTokens() = tokens.size

    /**
     * Lexes the whole input at once.
     */
    fun lexFully(): List<Token> {
        while (next() != null);
        return tokens
    }

    /**
     * Get the start column index of the currently active token.
     */
    fun column() = column - if (lexedTokens() > 1) current().value.length else 0
}