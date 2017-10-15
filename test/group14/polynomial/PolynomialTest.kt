package group14.polynomial

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertEquals

class PolynomialTest {

    @Test
    fun toPolynomialString() {
        val poly = Polynomial(3L, 2, 1)
        assertEquals("X + 2 (ℤ/3ℤ)", poly.toPolynomialString())

        val poly2 = Polynomial(3L, 2, 2)
        assertEquals("2X + 2 (ℤ/3ℤ)", poly2.toPolynomialString())
    }

    @Test
    fun `Zero polynomial toPolynomialString`() {
        val poly = Polynomial(3L)
        assertEquals("0 (ℤ/3ℤ)", poly.toPolynomialString())
    }

    @Test
    fun `Degree zero`() {
        val poly = Polynomial.zero(2L)
        assertEquals(0, poly.degree, "Polynomials without coefficients has degree 0")
    }

}