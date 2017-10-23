package group14.field

import group14.polynomial.Polynomial
import org.junit.Assert.assertEquals
import org.junit.Test

/*
 * @author Daan de Graaf
 */
class AdditionTableTest {

    @Test
    fun `Format`() {
        val poly = Polynomial(2, 1, 1, 1)
        val field = FiniteField(poly)
        val table = AdditionTable(field)

        val expected =
                """    |  0    1    X    X+1""" + System.lineSeparator() +
                        """-------------------------""" + System.lineSeparator() +
                        """0   |  0    1    X    X+1""" + System.lineSeparator() +
                        """1   |  1    0    X+1  X  """ + System.lineSeparator() +
                        """X   |  X    X+1  0    1  """ + System.lineSeparator() +
                        """X+1 |  X+1  X    1    0  """
        val actual = table.format(2, OperationTable.FormatStyle.PRETTY)
        println(actual)
        assertEquals(expected, actual)
    }
}