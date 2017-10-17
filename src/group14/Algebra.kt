package group14

import group14.evaluation.REPL

fun main(args: Array<String>) {
    REPL(System.out, emptySet())
//    REPL(System.out, EnumSet.of(Option.SHOW_STACKTRACE))
}

fun Any?.println() = println(this)