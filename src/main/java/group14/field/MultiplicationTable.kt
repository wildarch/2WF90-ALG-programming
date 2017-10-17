package group14.field

/**
 * Multiplication table for the given field
 * @author Daan de Graaf
 */
class MultiplicationTable(field: FiniteField): OperationTable(field::multiply, field)