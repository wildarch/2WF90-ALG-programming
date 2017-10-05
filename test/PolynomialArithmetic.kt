import group14.integer.ModularInteger
import group14.polynomial.Polynomial
import org.junit.Assert.*
import org.junit.Test

class PolynomialArithmetic {

    @Test
    fun `AddTest`() {
        println("Add")
        var p1 = Polynomial(5, 4, 3, 1, 2, 3)
        var p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        var expected = Polynomial(5, 0, 0, 3, 3, 0, 1, 2, 1)
        var result = p1 + p2

        assertEquals(expected.toString(), result.toString())

        var m1 = ModularInteger(4, 5);
        var expected2 = Polynomial(5, 3, 3, 1, 2, 3)
        var result2 = p1 + m1
        assertEquals(expected2.toString(), result2.toString())

        var expected3 = p1
        var result3 = p1 + Polynomial(emptyArray(), 5)
        assertEquals("Empty add", expected3.toString(), result3.toString())

        var expected4 = p1
        var result4 = Polynomial.zero(5) + p1
        assertEquals("Empty first parameter", expected4.toString(), result4.toString())
    }

    @Test
    fun `MinusTest`() {
        println("Minus")
        var p1 = Polynomial(5, 4, 3, 1, 2, 3)
        var p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        var expected = Polynomial(5, 3, 1, 4, 1, 1, 4, 3, 4)
        var result = p1 - p2
        assertEquals(expected.toString(), result.toString())

        var m1 = ModularInteger(4, 5);
        var expected2 = Polynomial(5, 0, 3, 1, 2, 3)
        var result2 = p1 - m1
        assertEquals(expected2.toString(), result2.toString())

        var expected3 = Polynomial(emptyArray(), 5)
        var result3 = p1 - p1
        assertEquals("Zero as result", expected3.toString(), result3.toString())

        var expected4 = p1
        var result4 = p1 - Polynomial(emptyArray(), 5)
        assertEquals("Empty subtract", expected4.toString(), result4.toString())

        var expected5 = Polynomial(5, 1, 2, 4, 3, 2)
        var result5 = Polynomial(emptyArray(), 5) - p1
        assertEquals("Empty first parameter", expected5.toString(), result5.toString())
    }

    @Test
    fun `MultTest`() {
        println("Multiply")
        var p1 = Polynomial(5, 4, 3, 1, 2, 3)
        var p2 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        var expected = Polynomial(5, 4, 1, 0, 4, 0, 1, 1, 3, 3, 3, 3, 3)
        var result = p1 * p2

        assertEquals(expected.toString(), result.toString())

        var m1 = ModularInteger(4, 5);
        var expected2 = Polynomial(5, 1, 2, 4, 3, 2)
        var result2 = p1 * m1
        assertEquals(expected2.toString(), result2.toString())

        var expected3 = Polynomial(emptyArray(), 5)
        var result3 = p1 * Polynomial(emptyArray(), 5)
        assertEquals("Empty multiply", expected3.toString(), result3.toString())

        var expected4 = Polynomial.zero(5)
        var result4 = Polynomial(emptyArray(), 5) * p1
        assertEquals("Empty first parameter", expected4.toString(), result4.toString())
    }

}