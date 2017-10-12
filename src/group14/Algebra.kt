package group14

import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser
import group14.parser.TreePrinter

fun main(args: Array<String>) {
    print(">>> ")
    val input = readLine() ?: ""

    try {
        val lexer = Lexer(input)
        val parser = Parser(lexer)
        val tree = parser.constructParseTree()
        println()
        TreePrinter(tree).print().println()
    }
    catch (e: ParseException) {
        System.err.println("    ${" ".repeat(e.column)}^")
        System.err.println("ParseException: ${e.message}")
    }
}

fun Any?.println() = println(this)