package group14.parser

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
    var column: Int = 0
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
            val name = type.name
            val group = matcher.group(name) ?: continue
            val token = Token(type, group)
            tokens.add(token)
            column += group.length
            return token
        }

        throw AssertionError("Should not happen!")
    }

    /**
     * Looks `distance` tokens behind.
     *
     * @throws IndexOutOfBoundsException When there is no token available.
     */
    @Throws(IndexOutOfBoundsException::class)
    fun lookBehind(distance: Int = 1) = tokens[tokens.size - 1 - distance]

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
}