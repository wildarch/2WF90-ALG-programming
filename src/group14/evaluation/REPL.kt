package group14.evaluation

import group14.Option
import group14.Primes
import group14.TaskState
import group14.launch
import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser
import group14.parser.TokenType
import java.io.BufferedReader
import java.io.InputStream
import java.io.PrintStream
import java.nio.Buffer

/**
 * Read-Eval-Print Loop
 *
 * @author Ruben Schellekens
 */
open class REPL constructor(val options: Set<Option> = setOf(), val input: BufferedReader = System.`in`.bufferedReader(), val output: PrintStream = System.out) {

    /**
     * loads the lexer regex asynchronously to prevent the user from noticing the loading time.
     */
    private var regexLoader: TaskState<Unit>? = null

    /**
     * Loads the primes from the file asynchronously to prevent the user from noticing the loading time.
     */
    private var primeLoader: TaskState<Unit>? = null

    /**
     * The current evaluation state. So helpful comment #Yay.
     */
    private var evaluationState: EvaluationState = EvaluationState(options)

    init {
        // Load lexer regex.
        regexLoader = launch {
            TokenType.LEXER_REGEX.matcher("")
        }

        // Load prime numbers.
        primeLoader = launch {
            Primes.isPrimeNumber(3)
        }

        // Let the fun begin.
        start()
    }

    /**
     * Starts the REPL.
     */
    private fun start() {
        if (!options.contains(Option.SKIP_INTRO))
                introduction()

        while (true) {
            val input = read() ?: break
            evaluate(input)
            println() // No output.println otherwise not needed newlines end up in file
        }
    }

    /**
     * Prompts the user for input.
     *
     * @return The input the user has inputted.
     */
    private fun read(): String? {
        print(">>> ") // No output.print otherwise not needed >>> ends up in file
        return input.readLine()
    }

    /**
     * Evaluates the given input.
     */
    private fun evaluate(input: String) {
        waitForLoads()

        // Parse input
        val lexer = Lexer(input)
        val parser = Parser(lexer)

        try {
            val tree = parser.constructParseTree()

            // Evaluate
            val evalutor = Evaluator.create(tree)
            evalutor.evaluate(tree, output, evaluationState)
        }
        catch (parseException: ParseException) {
            println("    ${" ".repeat(parseException.column)}^")
            output.println("ParseException: ${parseException.message}")

            if (Option.SHOW_STACKTRACE in options) {
                parseException.printStackTrace(output)
            }
        }
        catch (evaluationException: EvaluationException) {
            output.println("EvaluationException: ${evaluationException.message}")

            if (Option.SHOW_STACKTRACE in options) {
                evaluationException.printStackTrace(output)
            }
        }
    }

    /**
     * Prints the introduction.
     */
    private fun introduction() {
        // Present prompt.
        println("Polynomials and Finite Fields -- Group 14 “Java met een vleugje Kotlin”")
        println("Enter an expression to evaluate. Type ‘help’ for help or ‘exit’ to close the program.")
    }

    /**
     * Wait for the background loading tasks (primes & regex).
     */
    private fun waitForLoads() {
        // Make sure the regex is loaded.
        if (regexLoader != null) {
            regexLoader!!.wait()
            regexLoader = null
        }

        // Make sure the primes are loaded.
        if (primeLoader != null) {
            primeLoader!!.wait()
            primeLoader = null
        }
    }
}