package group14.field

import group14.Table
import group14.polynomial.Polynomial

/**
 * Table resulting from applying a binary operation on each pair of elements for the given field.
 * The two operands are in the headers.
 * @author Daan de Graaf
 */
open class OperationTable(operation: (a: Polynomial, b: Polynomial) -> Polynomial,
                     field: FiniteField) : Table<Polynomial>() {
    init {
        val elements = field.getElements().sorted()
        val rows = elements.map { a -> elements.map { b -> operation(a, b) } }
        addRowHeaders(*elements.map { it.toPolynomialString() }.toTypedArray())
        addColumnHeaders(*elements.map { it.toPolynomialString() }.toTypedArray())
        rows.forEach { addRow(it) }
    }

    enum class FormatStyle {
        PRETTY,
        COEFF_LIST
    }

    fun format(columnPadding: Int, style: FormatStyle): String {
        val formatter = when(style) {
            FormatStyle.PRETTY -> Polynomial::toPolynomialString
            FormatStyle.COEFF_LIST -> Polynomial::toString
        }
        return super.format(columnPadding, formatter)
    }
}