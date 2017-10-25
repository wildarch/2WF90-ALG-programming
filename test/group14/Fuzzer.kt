package group14

import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser
import org.junit.Test
import java.text.CharacterIterator
import java.util.*

class FuzzTester {
    @Test
    fun repl() {
        val r = Random()
        val chars = (' ' until '~').toList()

        while(true) {
            val length = r.nextInt(40)
            var str = ""
            for (i in 0 until length) {
                str += chars.get(r.nextInt(chars.size))
            }
            val parser = Parser(Lexer(str))
            try {
                parser.constructParseTree()
            } catch (e: ParseException) {
                // Nope
            } catch (e: Exception) {
                println("Can't parse: '$str'")
                println(e)
                kotlin.io.println()
            }
        }
    }
}