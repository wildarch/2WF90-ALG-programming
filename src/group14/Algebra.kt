package group14

import group14.integer.IntegerEuclids
import group14.integer.ModularInteger

fun main(args: Array<String>) {
    val euclids = IntegerEuclids(2, 5)
    val (x, y, gcd) = euclids.execute()
    val a = euclids.a
    val b = euclids.b
    println("gcd($a, $b) = $gcd = $a*($x) + $b*($y)")

    for (i in 1..4) {
        println("$i^-1 = ${ModularInteger(i.toLong(), 5).inverse()}")
    }
}

fun Any.println() = println(this)