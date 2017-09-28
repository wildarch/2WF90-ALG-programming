package group14

import group14.Primes.MAX_PRIME

/**
 * Stores & checks primes under [MAX_PRIME].
 *
 * @author Ruben Schellekens
 */
object Primes {

    /**
     * No prime in [Primes] is larger than this value.
     */
    @JvmStatic
    val MAX_PRIME = 10_000_000

    /**
     * Set containing all primes under 10_000_000.
     */
    private val primes: Set<Int>

    init {
        // Parse all primes.
        val primes = HashSet<Int>()
        val primesFile = javaClass.getResourceAsStream("/primes.txt")
        val line = primesFile.bufferedReader().readLine()

        val separator = ';'.toInt()
        val buffer = StringBuilder()
        line.chars().forEach {
            if (it == separator) {
                primes.add(Integer.parseInt(buffer.toString()))
                buffer.setLength(0)
            }
            else {
                buffer.append(it.toChar())
            }
        }

        this.primes = primes
    }

    /**
     * Checks if the given integer is prime or not.
     *
     * @param primeHuh
     *          The number to check if it's prime.
     * @return `true` if it is prime, `false` if it is not prime.
     * @throws IllegalArgumentException When the integer value exceeds [MAX_PRIME].
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun isPrimeNumber(other: Number): Boolean {
        val value = other.toInt()
        if (value > MAX_PRIME) {
            throw IllegalArgumentException("Integer is larger than MAX_PRIME=${MAX_PRIME}.")
        }

        return primes.contains(value)
    }
}

/**
 * Checks if the given integer is prime or not.
 *
 * @return `true` if it is prime, `false` if it is not prime.
 * @throws IllegalArgumentException When the integer value exceeds [MAX_PRIME].
 */
@Throws(IllegalArgumentException::class)
fun Number.isPrime() = Primes.isPrimeNumber(this)