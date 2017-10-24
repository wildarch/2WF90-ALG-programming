package group14.field

import group14.polynomial.Polynomial
import org.junit.Assert.assertEquals
import org.junit.Test

/*
 * @author Daan de Graaf
 */
class MultiplicationTableTest {

    @Test
    fun `Format`() {
        val poly = Polynomial(3, 2, 1, 1)
        val field = FiniteField(poly)
        val table = MultiplicationTable(field, FormatStyle.PRETTY)

        val expected = "     |  0  1     2     X     X+1   X+2   2X    2X+1  2X+2" + System.lineSeparator() +
                "---------------------------------------------------------" + System.lineSeparator() +
                "0    |  0  0     0     0     0     0     0     0     0   " + System.lineSeparator() +
                "1    |  0  1     2     X     X+1   X+2   2X    2X+1  2X+2" + System.lineSeparator() +
                "2    |  0  2     1     2X    2X+2  2X+1  X     X+2   X+1 " + System.lineSeparator() +
                "X    |  0  X     2X    2X+1  1     X+1   X+2   2X+2  2   " + System.lineSeparator() +
                "X+1  |  0  X+1   2X+2  1     X+2   2X    2     X     2X+1" + System.lineSeparator() +
                "X+2  |  0  X+2   2X+1  X+1   2X    2     2X+2  1     X   " + System.lineSeparator() +
                "2X   |  0  2X    X     X+2   2     2X+2  2X+1  X+1   1   " + System.lineSeparator() +
                "2X+1 |  0  2X+1  X+2   2X+2  X     1     X+1   2     2X  " + System.lineSeparator() +
                "2X+2 |  0  2X+2  X+1   2     2X+1  X     1     2X    X+2 "
        val actual = table.format(2, FormatStyle.PRETTY)
        println(actual)
        assertEquals(expected, actual)
    }
}