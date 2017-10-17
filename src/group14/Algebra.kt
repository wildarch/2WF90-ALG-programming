package group14

import group14.evaluation.EvaluationState
import group14.evaluation.arithmetic.ArithmeticEvaluation
import group14.evaluation.arithmetic.EvaluationObject
import group14.integer.mod
import group14.parser.Lexer
import group14.parser.TokenType
import group14.polynomial.Polynomial

fun main(args: Array<String>) {
    val lexer = Lexer("")

    val state = EvaluationState(modulus = 5)
    val list = mutableListOf<EvaluationObject>(
            3 mod 7,
            TokenType.ADD,
            2 mod 7
    )
    val evaluation = ArithmeticEvaluation(state, list)
    val list2 = mutableListOf<EvaluationObject>(
            evaluation,
            TokenType.ADD,
            2 mod 7
    )
    val evaluation2 = ArithmeticEvaluation(state, list2)
    val result = evaluation2.evaluate()

    val value = result.value()
    if (value is Polynomial) {
        println("Result: ${value.toPolynomialString()}")
    }
    else {
        println("Result: ${result.value()}")
    }

    "\nDone!".println()
//    REPL(System.out, emptySet())
}

fun Any?.println() = println(this)