package group14.parser

/**
 * @author Ruben Schellekens
 */
open class Lexer(val input: String) {

    fun lex(): List<Token> {
        val result = ArrayList<Token>()

        val matcher = TokenType.LEXER_REGEX.matcher(input)
        while (matcher.find()) {
            for (type in TokenType.values()) {
                val name = type.name
                val group = matcher.group(name) ?: continue
                result.add(Token(type, group))
                break
            }
        }

        return result
    }
}