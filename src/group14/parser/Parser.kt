package group14.parser

/**
 * @author Ruben Schellekens
 */
interface Parser<out T> {

    /**
     * Parses the given string.
     */
    fun parse(text: String): T
}