package group14

import group14.evaluation.REPL

fun main(args: Array<String>) {
    REPL(System.out)
}

fun Any?.println() = println(this)