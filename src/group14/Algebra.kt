package group14

import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser

fun main(args: Array<String>) {
//    val input = "findprimitives 3 + {_ + [X^2]} (mod 7) (field [X])"
    val input = "[X]+[X] (field n) (mod p)"
    println(">>> $input")

    try {
        val lexer = Lexer(input)
        val parser = Parser(lexer)
        println("\n${parser.constructParseTree()}")
    }
    catch (e: ParseException) {
        System.err.println(" ".repeat(4 + e.column) + "^")
        e.printStackTrace()
    }
}

fun Any?.println() = println(this)