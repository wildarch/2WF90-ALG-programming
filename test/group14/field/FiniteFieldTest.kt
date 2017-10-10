package group14.field

import group14.polynomial.Polynomial
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FiniteFieldTest {

    @Test
    fun `Generate elements`() {
        val poly = Polynomial(3, 1, 0, 1, 1)
        val field = FiniteField(poly)
        val elements = field.getElements()

        for (elem in elements) {
            println("$elem || ${elem.toPolynomialString()}")
        }
    }

    @Test
    fun `Is element`() {
        val poly = Polynomial(3, 1, 0, 1, 1)
        val field = FiniteField(poly)
        val elements = field.getElements()

        for (elem in elements) {
            assertTrue(field.isElement(elem), "All elements are classified as elements of the field")
        }

        val notElement = Polynomial(3, 1, 1, 1, 1, 1)
        assertFalse(field.isElement(notElement),
                "Element with degree 4 is not an element of a field with irreducible polynomial of degree 3")
    }

    @Test
    fun `Zero is an element`() {
        val poly = Polynomial(3, 1, 0, 1, 1)
        val field = FiniteField(poly)
        val zero = Polynomial.zero(3)
        assertTrue(field.isElement(zero), "Zero is an element of $field")
    }

    @Test
    fun `Addition`() {
        val poly = Polynomial(2, 1, 1, 1)
        val field = FiniteField(poly)
        val a = Polynomial(2, 1, 0)
        val b = Polynomial(2, 1, 1)
        val r = Polynomial(2, 0, 1)
        assertEquals(field.add(a, b), r)
    }

    @Test
    fun `Subtraction`() {
        val poly = Polynomial(2, 1, 1, 1, 1)
        val field = FiniteField(poly)
        val a = Polynomial(2, 0, 1)
        val b = Polynomial(2, 0, 0, 1)
        val r = Polynomial(2, 0, 1, 1)
        assertEquals(r, field.subtract(a, b),
                "${a.toPolynomialString()} - ${b.toPolynomialString()} != ${r.toPolynomialString()}")
    }

    @Test
    fun `Addition, subtraction and multiplication land in field`() {
        val poly = Polynomial(3, 1, 0, 1, 1)
        val field = FiniteField(poly)
        val elements = field.getElements()

        for (a in elements) {
            for (b in elements) {
                val add = field.add(a, b)
                assertTrue(field.isElement(add), "$a + $b = $add is outside of $field")
                val sub = field.subtract(a, b)
                assertTrue(field.isElement(sub), "$a - $b = $sub is outside of $field")
                val mul = field.multiply(a, b)
                assertTrue(field.isElement(sub), "$a * $b = $mul is outside of $field")
            }
        }
    }
}