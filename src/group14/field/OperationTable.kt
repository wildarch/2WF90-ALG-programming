package group14.field

import group14.Table
import group14.polynomial.Polynomial

/**
 * Table resulting from applying a binary operation on each pair of elements for the given field.
 * The two operands are in the headers.
 *
 * @author Daan de Graaf
 */
open class OperationTable(operation: (Polynomial, Polynomial) -> Polynomial, field: FiniteField) : Table<Polynomial>() {

    init {
        val elements = field.getElements().sorted()
        val rows = elements.map { a -> elements.map { b -> operation(a, b) } }
        addRowHeaders(*elements.map { it.toPolynomialString { "" } }.toTypedArray())
        addColumnHeaders(*elements.map { it.toPolynomialString { "" } }.toTypedArray())
        rows.forEach { addRow(it) }
    }

    /**
     * Formats the table as human-readable String.
     *
     * @param columnPadding
     *          The number of characters of spacing between each column
     * @param style
     *          The formatting style for polynomial elements
     * @return Human-readable table
     */
    fun format(columnPadding: Int, style: FormatStyle): String {
        return super.format(columnPadding, style.styler)
    }

    /**
     * Converts polynomials to strings.
     */
    interface PolynomialStyler {

        val styler: (Polynomial) -> String
    }

    /**
     * Formatting style for the elements of the table.
     */
    enum class FormatStyle(override val styler: (Polynomial) -> String) : PolynomialStyler {

        /**
         * Use of pretty polynomial representation using [Polynomial.toPolynomialString].
         */
        PRETTY({ it.toPolynomialString { "" } }),

        /**
         * Use of coefficient list representation using [Polynomial.toString]
         */
        COEFFICIENT_LIST({ it.toString() })
    }
}