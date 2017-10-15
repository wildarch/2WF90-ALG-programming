package group14.field

import group14.polynomial.Polynomial
import org.junit.Test

import org.junit.Assert.*

class AdditionTableTest {
    @Test
    fun format() {
        val poly = Polynomial(2, 1, 1, 1)
        val field = FiniteField(poly)
        val table = AdditionTable(field)

        val expected =
                """             |  0 (ℤ/2ℤ)       1 (ℤ/2ℤ)       X (ℤ/2ℤ)       X + 1 (ℤ/2ℤ) """ + System.lineSeparator() +
                """––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––""" + System.lineSeparator() +
                """0 (ℤ/2ℤ)     |  0 (ℤ/2ℤ)       1 (ℤ/2ℤ)       X (ℤ/2ℤ)       X + 1 (ℤ/2ℤ) """ + System.lineSeparator() +
                """1 (ℤ/2ℤ)     |  1 (ℤ/2ℤ)       0 (ℤ/2ℤ)       X + 1 (ℤ/2ℤ)   X (ℤ/2ℤ)     """ + System.lineSeparator() +
                """X (ℤ/2ℤ)     |  X (ℤ/2ℤ)       X + 1 (ℤ/2ℤ)   0 (ℤ/2ℤ)       1 (ℤ/2ℤ)     """ + System.lineSeparator() +
                """X + 1 (ℤ/2ℤ) |  X + 1 (ℤ/2ℤ)   X (ℤ/2ℤ)       1 (ℤ/2ℤ)       0 (ℤ/2ℤ)     """
        val actual = table.format(2, OperationTable.FormatStyle.PRETTY)
        println(actual)
        assertEquals(expected, actual)
    }

}