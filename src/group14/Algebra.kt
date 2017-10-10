package group14

import group14.parser.Lexer

fun main(args: Array<String>) {
    val input = "[6*X^8+19X^4-4X^3--4X^2+X+-13] + [x+1] (mod 11)"

    val result = Lexer(input).lexFully()

    println(">>> $input")
    result.forEach(::println)
    println("\n${result.joinToString("") { it.value }}")
}

fun Any?.println() = println(this)