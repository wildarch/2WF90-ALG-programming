package group14.polynomial

import group14.integer.ModularInteger
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
            if (t == 10000) throw TimeoutException("Generating is stuck")
        }
        return f
    }
}