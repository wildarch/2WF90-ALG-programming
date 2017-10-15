package group14.polynomial

import group14.integer.ModularInteger
import group14.polynomial.Polynomial
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PolynomialLongDivision {

    @Test
    fun `DivTest`() {
        println("Div")
        var p2 = Polynomial(5, 4, 3, 1, 2, 3)
        var p1 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        var result = p1 / p2

        Assert.assertEquals(Polynomial(5, 1,2,2,1,2,1,2,1).toPolynomialString(), p1.toPolynomialString())

        p1 = Polynomial(5, 0)
        p2 = Polynomial(5, 1,0,1,3)

        result = p1/p2

        Assert.assertEquals(result.first*result.second, Polynomial.zero(5))


        p1 = Polynomial(5, 1,3,4)
        p2 = Polynomial(5, 1,0,1,3)

        result = p1/p2

        Assert.assertEquals((result.first * p2 + result.second).toPolynomialString(), p1.toPolynomialString())

        p1 = Polynomial(5, 4)
        p2 = Polynomial(5, 2)

        result = p1/p2

        Assert.assertEquals((result.first * p2 + result.second).toPolynomialString(), p1.toPolynomialString())

    }

    @Test
    fun `CongruentTest`() {
        println("Congruent")
        var a = Polynomial(5, 1,1,1)
        var b = Polynomial(5, 1,2,0,1)
        var d = Polynomial(5, 0,1,4, 1)
        assertTrue { a.congruent(b,d) }
        b = Polynomial(5, 1,2,1,2)
        assertFalse { a.congruent(b, d) }

        a = Polynomial(5, 1,4,2,1,2,3,1,4,1)
        b = a - d

        assertTrue { a.congruent(b, d) }

        a = Polynomial(5, 3)
        b = Polynomial(5, 2)
        assertFalse { a.congruent(b, d) }

        d = Polynomial(5, 0,1)

        a = Polynomial(5, 1,4,2,1,2,3,1,4,1)
        b = a - d

        assertTrue { a.congruent(b, d) }

        a = Polynomial(5, 3)
        b = Polynomial(5, 2)
        assertFalse { a.congruent(b, d) }
    }

}