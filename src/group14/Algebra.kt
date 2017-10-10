package group14

import group14.parser.Lexer
import group14.parser.Parser

fun main(args: Array<String>) {
//    val input = "[6*X^8+19X^4-4X^3--4X^2+X+-13] + [x+1] (mod 11)"
//    val input = "(mod 23) + (mod 17)"
    val input = "[3, 6, 7] (mod 181) = [3+16*X-7X^2] (mod [181+3X^2-16*X^9])"
    println(">>> $input\n")

    val lexer = Lexer(input)
    val parser = Parser(lexer)
    parser.constructParseTree().println()
}

fun Any?.println() = println(this)