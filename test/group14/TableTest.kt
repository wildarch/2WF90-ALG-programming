package group14

import org.junit.Test

import org.junit.Assert.*

class TableTest {
    @Test
    fun format() {
        val expected =
                """       |   Hallo      Chinees?""" + System.lineSeparator() +
                """------------------------------""" + System.lineSeparator() +
                """Hello  |   1          2       """ + System.lineSeparator() +
                """World! |   44444444   12      """
        val actual = with(Table<Int>()) {
            addColumnHeaders("Hallo", "Chinees?")
            addRowHeaders("Hello", "World!")
            addRow(1, 2)
            addRow(44444444, 12)
            format(3)
        }
        assertEquals(expected, actual)
        println(actual)
    }

}