package group14.parser

import group14.parser.TokenType.*
import group14.println
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Ruben Schellekens
 */
class LexerTest {

    @Test
    fun `Lexed token count`() {
        // Empty lexer.
        val lexer0 = Lexer("")
        val result0 = lexer0.lexFully()
        assertEquals(0, lexer0.lexedTokens(), "No lexed tokens via lexedTokens()")
        assertEquals(result0.size, lexer0.lexedTokens(), "lexedTokens() matching token list size")

        // Some weird input
        val lexer1 = Lexer("[3, 4, 5]=[6x^2+4x]^-1 (mod12) + -14")
        val result1 = lexer1.lexFully()
        assertEquals(29, lexer1.lexedTokens(), "29 tokens via lexedTokens()")
        assertEquals(result1.size, lexer1.lexedTokens(), "lexedTokens() matching token list size")

        // Intermediate values (10 tokens)
        val lexer2 = Lexer("[4,\t 5,0,1]")
        for (i in 1..7) {
            lexer2.next()
        }
        assertEquals(7, lexer2.lexedTokens(), "7 tokens via lexedTokens() - intermediate")
    }

    @Test
    fun `Column position`() {
        // Empty lexer.
        val lexer0 = Lexer("")
        assertEquals(0, lexer0.column(), "Nothing to lex")

        // Intermediate values (10 tokens)
        val lexer1 = Lexer("[42,\t 5,0,1]")
        for (i in 1..7) {
            lexer1.next()
        }
        assertEquals(8, lexer1.column(), "Lexing 8 tokens")
    }

    @Test
    fun `Lexing`() {
        // Check some tokens from a single sample.
        val lexer = Lexer("[3,\t 4, 5] =  [6x^2 -4X]^-1 (mod 12) + -14")
        checkToken(lexer.next(), OPENBRACKET, "[")
        checkToken(lexer.next(), NUMBER, "3")
        checkToken(lexer.next(), SEPARATOR, ",")
        checkToken(lexer.next(), WHITESPACE, "\t ")
        checkToken(lexer.next(), NUMBER, "4")
        checkToken(lexer.next(), SEPARATOR, ",")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), NUMBER, "5")
        checkToken(lexer.next(), CLOSEBRACKET, "]")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), EQUALS, "=")
        checkToken(lexer.next(), WHITESPACE, "  ")
        checkToken(lexer.next(), OPENBRACKET, "[")
        checkToken(lexer.next(), NUMBER, "6")
        checkToken(lexer.next(), PARAMETER, "x")
        checkToken(lexer.next(), POWER, "^")
        checkToken(lexer.next(), NUMBER, "2")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), NUMBER, "-4")
        checkToken(lexer.next(), PARAMETER, "X")
        checkToken(lexer.next(), CLOSEBRACKET, "]")
        checkToken(lexer.next(), INVERSE, "^-1")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), OPENPARENTHESIS, "(")
        checkToken(lexer.next(), MODKEYWORD, "mod")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), NUMBER, "12")
        checkToken(lexer.next(), CLOSEPARENTHESIS, ")")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), ADD, "+")
        checkToken(lexer.next(), WHITESPACE, " ")
        checkToken(lexer.next(), NUMBER, "-14")
    }

    private fun checkToken(actual: Token?, type: TokenType, text: String) {
        assertEquals("<$type $text>", actual.toString(), "Check token")
    }
}