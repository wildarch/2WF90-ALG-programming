package group14

import group14.polynomial.Polynomial

fun main(args: Array<String>) {
    val test = Polynomial(7, 2, 1, 2, 3, 0, 2, 3)
    test.toPolynomialString().println()
}

fun Any.println() = println(this)