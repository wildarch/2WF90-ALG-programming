package group14.field

import group14.Table
import group14.polynomial.Polynomial

/**
 * Addition table for the given field
 * @author Daan de Graaf
 */
class AdditionTable(field: FiniteField) : OperationTable(field::add, field)