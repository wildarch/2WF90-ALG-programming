import group14.integer.ModularInteger
import group14.polynomial.Polynomial
import org.junit.Assert
import org.junit.Test

class PolynomialLongDivision {

    @Test
    fun `DivTest`() {
        println("Div")
        val p2 = Polynomial(5, 4, 3, 1, 2, 3)
        val p1 = Polynomial(5, 1, 2, 2, 1, 2, 1, 2, 1)

        val result = p1 / p2

        println(result.first.toString())
        println(result.second.toString())


        val a = result.first * p2

        println(a.toPolynomialString())

        println((a+result.second).toPolynomialString())



        //Assert.assertEquals((result.first * p2 + result.second).toPolynomialString(), ())

        /*val m1 = ModularInteger(4, 5);
        val expected2 = Polynomial(5, 3, 3, 1, 2, 3)
        val result2 = p1 + m1
        Assert.assertEquals(expected2.toString(), result2.toString())

        val expected3 = p1
        val result3 = p1 + Polynomial(emptyArray(), 5)
        Assert.assertEquals("Empty add", expected3.toString(), result3.toString())

        val expected4 = p1
        val result4 = Polynomial.zero(5) + p1
        Assert.assertEquals("Empty first parameter", expected4.toString(), result4.toString())*/
    }

}