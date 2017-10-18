package group14

import group14.Primes.MAX_PRIME
import java.math.BigInteger
import java.util.*

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
        val value = other.toLong()
        if (value > MAX_PRIME) {
            return if (BigInteger.valueOf(value).isProbablePrime(128)) {
                System.err.println("Integer is larger than $MAX_PRIME, we cannot guarantee it's prime.")
                true
            }
            else {
                false
            }
        }

        return primes.contains(value.toInt())
    }

    /**
     * Generates a random prime `< bound`.
     *
     * @param bound
     *          The maximum size of the prime, exclusive. Must be smaller than [MAX_PRIME].
     * @param random
     *          The random object to use to generate the random prime.
     */
    @JvmStatic
    fun random(bound: Int = MAX_PRIME, random: Random = Random()): Int {
        require(bound <= MAX_PRIME) { "bound is not smaller than MAX_PRIME ($bound > $MAX_PRIME)" }
        val list = primes
                .filter { it < bound }
                .toList()
        return list[random.nextInt(list.size)]
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