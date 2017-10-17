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
        val table = MultiplicationTable(field)

        val expected =
                """              |  0 (ℤ/3ℤ)  1 (ℤ/3ℤ)       2 (ℤ/3ℤ)       X (ℤ/3ℤ)       X + 1 (ℤ/3ℤ)   X + 2 (ℤ/3ℤ)   2X (ℤ/3ℤ)      2X + 1 (ℤ/3ℤ)  2X + 2 (ℤ/3ℤ)""" + System.lineSeparator() +
                """–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––""" + System.lineSeparator() +
                """0 (ℤ/3ℤ)      |  0 (ℤ/3ℤ)  0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)       0 (ℤ/3ℤ)     """ + System.lineSeparator() +
                """1 (ℤ/3ℤ)      |  0 (ℤ/3ℤ)  1 (ℤ/3ℤ)       2 (ℤ/3ℤ)       X (ℤ/3ℤ)       X + 1 (ℤ/3ℤ)   X + 2 (ℤ/3ℤ)   2X (ℤ/3ℤ)      2X + 1 (ℤ/3ℤ)  2X + 2 (ℤ/3ℤ)""" + System.lineSeparator() +
                """2 (ℤ/3ℤ)      |  0 (ℤ/3ℤ)  2 (ℤ/3ℤ)       1 (ℤ/3ℤ)       2X (ℤ/3ℤ)      2X + 2 (ℤ/3ℤ)  2X + 1 (ℤ/3ℤ)  X (ℤ/3ℤ)       X + 2 (ℤ/3ℤ)   X + 1 (ℤ/3ℤ) """ + System.lineSeparator() +
                """X (ℤ/3ℤ)      |  0 (ℤ/3ℤ)  X (ℤ/3ℤ)       2X (ℤ/3ℤ)      2X + 1 (ℤ/3ℤ)  1 (ℤ/3ℤ)       X + 1 (ℤ/3ℤ)   X + 2 (ℤ/3ℤ)   2X + 2 (ℤ/3ℤ)  2 (ℤ/3ℤ)     """ + System.lineSeparator() +
                """X + 1 (ℤ/3ℤ)  |  0 (ℤ/3ℤ)  X + 1 (ℤ/3ℤ)   2X + 2 (ℤ/3ℤ)  1 (ℤ/3ℤ)       X + 2 (ℤ/3ℤ)   2X (ℤ/3ℤ)      2 (ℤ/3ℤ)       X (ℤ/3ℤ)       2X + 1 (ℤ/3ℤ)""" + System.lineSeparator() +
                """X + 2 (ℤ/3ℤ)  |  0 (ℤ/3ℤ)  X + 2 (ℤ/3ℤ)   2X + 1 (ℤ/3ℤ)  X + 1 (ℤ/3ℤ)   2X (ℤ/3ℤ)      2 (ℤ/3ℤ)       2X + 2 (ℤ/3ℤ)  1 (ℤ/3ℤ)       X (ℤ/3ℤ)     """ + System.lineSeparator() +
                """2X (ℤ/3ℤ)     |  0 (ℤ/3ℤ)  2X (ℤ/3ℤ)      X (ℤ/3ℤ)       X + 2 (ℤ/3ℤ)   2 (ℤ/3ℤ)       2X + 2 (ℤ/3ℤ)  2X + 1 (ℤ/3ℤ)  X + 1 (ℤ/3ℤ)   1 (ℤ/3ℤ)     """ + System.lineSeparator() +
                """2X + 1 (ℤ/3ℤ) |  0 (ℤ/3ℤ)  2X + 1 (ℤ/3ℤ)  X + 2 (ℤ/3ℤ)   2X + 2 (ℤ/3ℤ)  X (ℤ/3ℤ)       1 (ℤ/3ℤ)       X + 1 (ℤ/3ℤ)   2 (ℤ/3ℤ)       2X (ℤ/3ℤ)    """ + System.lineSeparator() +
                """2X + 2 (ℤ/3ℤ) |  0 (ℤ/3ℤ)  2X + 2 (ℤ/3ℤ)  X + 1 (ℤ/3ℤ)   2 (ℤ/3ℤ)       2X + 1 (ℤ/3ℤ)  X (ℤ/3ℤ)       1 (ℤ/3ℤ)       2X (ℤ/3ℤ)      X + 2 (ℤ/3ℤ) """
        val actual = table.format(2, OperationTable.FormatStyle.PRETTY)
        println(actual)
        assertEquals(expected, actual)
    }
}