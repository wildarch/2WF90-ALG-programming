package group14.integer

/**
 * The extended euclidian algorithm for two integers.
 *
 * @author Ruben Schellekens
 */
open class IntegerEuclids(
        val a: Long,
        val b: Long
) {

    var x: Long? = null
        private set(value) {
            field = value
        }

    var y: Long? = null
        private set(value) {
            field = value
        }

    var gcd: Long? = null
        private set(value) {
            field = value
        }

    /**
     * Executes the extended euclidean algorithm/
     */
    fun execute(): Triple<Long, Long, Long> {
        // TODO: Implement.

        return Triple(x!!, y!!, gcd!!)
    }
}