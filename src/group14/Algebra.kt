package group14

import group14.evaluation.REPL

fun main(args: Array<String>) {
    // Read command line flags.
    val options = args
            .mapNotNull { Option.fromFlag(it) }
            .toSet()

    REPL(System.out, options)
}

fun Any?.println() = println(this)