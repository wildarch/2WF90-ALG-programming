import group14.polynomial.Polynomial
import org.junit.Assert.*
import org.junit.Test

class PolynomialArithmetic {

    @Test
    fun `AddTest`() {
        var p1 = Polynomial(5, 4,3,1,2,3)
        var p2 = Polynomial(5, 1,2,2,1,2,1,2,1)

        var expected = Polynomial(5, 0,0,3,0,0,1,2,1)
        var result = p1+p2

        assertEquals(expected.toPolynomialString(), result.toPolynomialString())
    }
}