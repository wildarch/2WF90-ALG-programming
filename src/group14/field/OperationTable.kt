package group14.field

import group14.Option
import group14.Table
import group14.evaluation.EvaluationState
import group14.polynomial.Polynomial
import group14.polynomial.PolynomialStyler
import group14.superScript

/**
 * Table resulting from applying a binary operation on each pair of elements for the given field.
 * The two operands are in the headers.
 *
 * @author Daan de Graaf
 */
open class OperationTable(operation: (Polynomial, Polynomial) -> Polynomial, field: FiniteField, style: PolynomialStyler) : Table<Polynomial>() {

    init {
        val elements = field.getElements().sorted()
        val rows = elements.map { a -> elements.map { b -> operation(a, b) } }
        addRowHeaders(*elements.map { style.styler(it).substringBefore("(", "ERROR").trim() }.toTypedArray())
        addColumnHeaders(*elements.map { style.styler(it).substringBefore("(", "ERROR").trim() }.toTypedArray())
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
    fun format(columnPadding: Int, style: PolynomialStyler): String {
        return super.format(columnPadding, {style.styler(it).substringBefore("(", "ERROR").trim()})
    }
}