package group14

import group14.evaluation.REPL
import java.io.File
import java.io.PrintStream

fun main(args: Array<String>) {
    var inputStream = System.`in`
    val outputStream = MultiOutputStream(System.out)

    // Read command line flags.
    val options = args
            .mapNotNull { Option.fromFlag(it) }
            .toSet()

    if ("-f" in args) {
        val inputPath : String
        try {
            inputPath = args.get(args.indexOf("-f") + 1)
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Your program arguments are not in the correct format.")
        }
        inputStream = File(inputPath).inputStream()

    }

    if ("-o" in args) {
        val outputPath : String
        try {
            outputPath = args.get(args.indexOf("-o") + 1)
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Your program arguments are not in the correct format.")
        }
        val printStream = PrintStream(File(outputPath))
        outputStream.add(printStream)

    }

    REPL(options, inputStream.bufferedReader(), PrintStream(outputStream))
}

fun Any?.println() = println(this)