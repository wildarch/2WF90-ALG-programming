package group14.polynomial

import group14.integer.ModularInteger
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertTrue

class PolynomialIrreducible {

    @Test
    fun `CheckIrr`() {
        println("Irreducible")
        Assert.assertTrue(Polynomial(2, 1, 1, 0, 1).isIrreducible())
        Assert.assertTrue(Polynomial(2, 1, 1, 0, 0, 1).isIrreducible())
        Assert.assertTrue(Polynomial(2, 1, 1, 0, 1, 1, 1).isIrreducible())
        assertTrue { IrreducibleGenerator(3,4).generate().isIrreducible() }
    }

}