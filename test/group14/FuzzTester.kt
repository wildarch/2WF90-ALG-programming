package group14

import group14.parser.Lexer
import group14.parser.Parser
import org.junit.Test
import java.util.*

class FuzzTester {

    @Test
    fun repl() {
        val r = Random(92831)
        val chars = (' ' until '~').toList()

        for (iteration in 1..500) {
            val length = r.nextInt(40)
            var str = ""
            for (i in 0 until length) {
                str += chars[r.nextInt(chars.size)]
            }
            val parser = Parser(Lexer(str))
            try {
                parser.constructParseTree()
            }
            catch (e: Exception) {
                println("Can't parse: '$str'")
                println(e)
                kotlin.io.println()
            }
        }
    }
}