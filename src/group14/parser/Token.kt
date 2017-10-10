package group14.parser

/**
 * @author Ruben Schellekens
 */
data class Token(val type: TokenType, val value: String) {

    /**
     * @return `(TYPE text)`
     */
    override fun toString(): String {
        return "(${type.name} $value}"
    }
}