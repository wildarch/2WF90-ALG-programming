package group14

import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser
import group14.parser.TreePrinter

fun main(args: Array<String>) {
    val input = "findprimitives 3 + {_ + [X^2] * {4 + {5 - [4,5]} ^ {2}}} (mod 7) (field [X])"
//    val input = "{3^-1 - {3}} + {[X^2] ^ {3}} (mod 5) (field 7) * {14/{3}} % {{4}%{5}}"
//    val input = "def kameel = [X^2+3] + (@mod7) "
    println(">>> $input")

    try {
        val lexer = Lexer(input)
        val parser = Parser(lexer)
        val tree = parser.constructParseTree()

        println("\n${TreePrinter(tree,4).print()}")
    }
    catch (e: ParseException) {
        System.err.println(" ".repeat(4 + e.column) + "^")
        e.printStackTrace()
    }
}

fun Any?.println() = println(this)