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
                """             |  0 (ℤ/2ℤ)      1 (ℤ/2ℤ)      X (ℤ/2ℤ)      X + 1 (ℤ/2ℤ)""" + System.lineSeparator() +
                """––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––""" + System.lineSeparator() +
                """0 (ℤ/2ℤ)     |  0 (ℤ/2ℤ)      1 (ℤ/2ℤ)      X (ℤ/2ℤ)      X + 1 (ℤ/2ℤ)""" + System.lineSeparator() +
                """1 (ℤ/2ℤ)     |  1 (ℤ/2ℤ)      0 (ℤ/2ℤ)      X + 1 (ℤ/2ℤ)  X (ℤ/2ℤ)    """ + System.lineSeparator() +
                """X (ℤ/2ℤ)     |  X (ℤ/2ℤ)      X + 1 (ℤ/2ℤ)  0 (ℤ/2ℤ)      1 (ℤ/2ℤ)    """ + System.lineSeparator() +
                """X + 1 (ℤ/2ℤ) |  X + 1 (ℤ/2ℤ)  X (ℤ/2ℤ)      1 (ℤ/2ℤ)      0 (ℤ/2ℤ)    """
        val actual = table.format(2, OperationTable.FormatStyle.PRETTY)
        println(actual)
        assertEquals(expected, actual)
    }
}