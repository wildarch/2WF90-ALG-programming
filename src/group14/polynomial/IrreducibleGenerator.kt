package group14.polynomial

import java.util.concurrent.TimeoutException

open class IrreducibleGenerator(val modulus: Long, val degree: Int) {

    /**
     * Returns an irreducible polynomial of given degree and modulus
     */
    fun generate(): Polynomial {
        require(degree > 0, { "Degree must be positive" })
        var t = 0
        var f = Polynomial.random(modulus, degree)
        while (!f.isIrreducible()) {
            f = Polynomial.random(modulus, degree)
            t++
        }
        return f
    }
}