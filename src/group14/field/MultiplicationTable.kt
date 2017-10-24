package group14.field

import group14.polynomial.PolynomialStyler

/**
 * Multiplication table for the given field
 * @author Daan de Graaf
 */
class MultiplicationTable(field: FiniteField, style: PolynomialStyler): OperationTable(field::multiply, field, style)