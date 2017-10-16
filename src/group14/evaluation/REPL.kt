package group14.evaluation

import group14.TaskState
import group14.launch
import group14.parser.Lexer
import group14.parser.ParseException
import group14.parser.Parser
import group14.parser.TokenType
import java.io.PrintStream

/**
 * Read-Eval-Print Loop
 *
 * @author Ruben Schellekens
 */
open class REPL(val output: PrintStream) {

    /**
     * loads the lexer regex asynchronously to prevent the user from noticing the loading time.
     */
    private var regexLoader: TaskState<Unit>? = null

    /**
     * The current evaluation state. So helpful comment #Yay.
     */
    private var evaluationState: EvaluationState = EvaluationState()

    init {
        // Load lexer regex.
        regexLoader = launch { TokenType.LEXER_REGEX.matcher("") }

        // Let the fun begin.
        start()
    }

    /**
     * Starts the REPL.
     */
    private fun start() {
        introduction()

        while (true) {
            val input = read()
            evaluate(input)
            output.println()
        }
    }

    /**
     * Prompts the user for input.
     *
     * @return The input the user has inputted.
     */
    private fun read(): String {
        print(">>> ")
        return readLine() ?: ""
    }

    /**
     * Evaluates the given input.
     */
    private fun evaluate(input: String) {
        waitForRegex()

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
            output.println("    ${" ".repeat(parseException.column)}^")
            output.println("ParseException: ${parseException.message}")
            return
        }
    }

    /**
     * Prints the introduction.
     */
    private fun introduction() {
        // Present prompt.
        output.println("Polynomials and Finite Fields -- Group 14 “Java met een vleugje Kotlin”")
        output.println("Enter an expression to evaluate. Type ‘help’ for help or ‘exit’ to close the program.")
    }

    /**
     * When the regex is being loaded, wait for it to load.
     */
    private fun waitForRegex() {
        // Make sure the regex is loaded.
        if (regexLoader != null) {
            regexLoader!!.wait()
            regexLoader = null
        }
    }
}