package group14.field

/**
 * Addition table for the given field
 * @author Daan de Graaf
 */
class AdditionTable(field: FiniteField) : OperationTable(field::add, field)