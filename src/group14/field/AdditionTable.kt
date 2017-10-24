package group14.field

import group14.polynomial.PolynomialStyler

/**
 * Addition table for the given field
 * @author Daan de Graaf
 */
class AdditionTable(field: FiniteField, style: PolynomialStyler) : OperationTable(field::add, field, style)