package group14.polynomial
import group14.integer.ModularInteger
import group14.polynomial.Polynomial
import org.junit.Assert.assertEquals
import org.junit.Test

class PolynomialArithmetic {

    @Test
    fun `AddTest`() {
        println("Add")
        val p1 = Polynomial(5, 4, 3, 1, 2, 3)
        val p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        val expected = Polynomial(5, 0, 0, 3, 3, 0, 1, 2, 1)
        val result = p1 + p2

        assertEquals(expected.toString(), result.toString())

        val m1 = ModularInteger(4, 5);
        val expected2 = Polynomial(5, 3, 3, 1, 2, 3)
        val result2 = p1 + m1
        assertEquals(expected2.toString(), result2.toString())

        val expected3 = p1
        val result3 = p1 + Polynomial(emptyArray(), 5)
        assertEquals("Empty add", expected3.toString(), result3.toString())

        val expected4 = p1
        val result4 = Polynomial.zero(5) + p1
        assertEquals("Empty first parameter", expected4.toString(), result4.toString())

        val expected5 = Polynomial(5, 3)
        val result5 = Polynomial.zero(5)+ModularInteger(3,5)

        assertEquals(expected5.toPolynomialString(), result5.toPolynomialString())

        val expected6 = Polynomial(5, 2)
        val result6 = Polynomial.zero(5)-ModularInteger(3,5)

        assertEquals(expected6.toPolynomialString(), result6.toPolynomialString())
    }

    @Test
    fun `MinusTest`() {
        println("Minus")
        val p1 = Polynomial(5, 4, 3, 1, 2, 3)
        val p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        val expected = Polynomial(5, 3, 1, 4, 1, 1, 4, 3, 4)
        val result = p1 - p2
        assertEquals(expected.toString(), result.toString())

        val m1 = ModularInteger(4, 5);
        val expected2 = Polynomial(5, 0, 3, 1, 2, 3)
        val result2 = p1 - m1
        assertEquals(expected2.toString(), result2.toString())

        val expected3 = Polynomial(emptyArray(), 5)
        val result3 = p1 - p1
        assertEquals("Zero as result", expected3.toString(), result3.toString())

        val expected4 = p1
        val result4 = p1 - Polynomial(emptyArray(), 5)
        assertEquals("Empty subtract", expected4.toString(), result4.toString())

        val expected5 = Polynomial(5, 1, 2, 4, 3, 2)
        val result5 = Polynomial(emptyArray(), 5) - p1
        assertEquals("Empty first parameter", expected5.toString(), result5.toString())
    }

    @Test
    fun `MultTest`() {
        println("Multiply")
        val p1 = Polynomial(5, 4, 3, 1, 2, 3)
        val p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        val expected = Polynomial(5, 4, 1, 0, 4, 0, 1, 1, 3, 3, 3, 3, 3)
        val result = p1 * p2

        assertEquals(expected.toString(), result.toString())

        val m1 = ModularInteger(4, 5);
        val expected2 = Polynomial(5, 1, 2, 4, 3, 2)
        val result2 = p1 * m1
        assertEquals(expected2.toString(), result2.toString())

        val expected3 = Polynomial(emptyArray(), 5)
        val result3 = p1 * Polynomial(emptyArray(), 5)
        assertEquals("Empty multiply", expected3.toString(), result3.toString())

        val expected4 = Polynomial.zero(5)
        val result4 = Polynomial(emptyArray(), 5) * p1
        assertEquals("Empty first parameter", expected4.toString(), result4.toString())
    }

}