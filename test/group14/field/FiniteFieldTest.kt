package group14.field

import group14.polynomial.Polynomial
import org.junit.Assert.*
import org.junit.Test

class FiniteFieldTest {
    @Test
    fun `Elements`() {
        val poly = Polynomial(3, 1, 0, 1, 1)
        val field = FiniteField(poly)
        val elements = field.getElements()

        for (elem in elements) {
            println("${elem} || ${elem.toPolynomialString()}")
        }
    }
}