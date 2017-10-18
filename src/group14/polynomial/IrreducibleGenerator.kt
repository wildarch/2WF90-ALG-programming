package group14.polynomial

import java.util.*

open class IrreducibleGenerator(val modulus: Long, val degree: Int) {

    /**
     * The random object used to generate random polynomials.
     */
    var random = Random()

    /**
     * Returns an irreducible polynomial of given degree and modulus
     */
    fun generate(): Polynomial {
        require(degree > 0, { "Degree must be positive" })
        var t = 0
        var f = Polynomial.random(modulus, degree, random)
        while (!f.isIrreducible()) {
            f = Polynomial.random(modulus, degree, random)
            t++
        }
        return f
    }
}