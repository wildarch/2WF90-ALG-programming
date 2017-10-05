package group14.polynomial

import org.junit.Test

import org.junit.Assert.*

class PolynomialTest {
    @Test
    fun toPolynomialString() {
        val poly = Polynomial(3L, 2, 1)
        assertEquals("X + 2 (ℤ/3ℤ)", poly.toPolynomialString())

        val poly2 = Polynomial(3L, 2, 2)
        assertEquals("2X + 2 (ℤ/3ℤ)", poly2.toPolynomialString())
    }

}