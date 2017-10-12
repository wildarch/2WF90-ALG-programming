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
    fun `Degree zero`() {
        val poly = Polynomial.zero(2L)
        assertEquals(0, poly.degree, "Polynomials without coefficients has degree 0")
    }

    @Test
    fun `Singular`() {
        assertEquals(Polynomial.singular(5, 4).toPolynomialString(), Polynomial(5, 0, 0, 0, 0, 1).toPolynomialString())
        assertEquals(Polynomial.singular(5, 0).toPolynomialString(), Polynomial(5, 1).toPolynomialString())
    }

    @Test
    fun `Random`() {
        for (i in 0..100) {
            var poly = Polynomial.random(5, 3)
            assertEquals(3, poly.degree)
        }
    }

}