package group14

import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser

fun main(args: Array<String>) {
//    val input = "[6*X^8+19X^4-4X^3--4X^2+X+-13] + [x+1] (mod 11)"
    val input = "3 + [5, 5, 0] ^ 7"
//    val input = "[3, 6, 7] * -5 (mod 181) = [3+16*X-7X^2] (mod [181+3X^-16*X^9])"
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