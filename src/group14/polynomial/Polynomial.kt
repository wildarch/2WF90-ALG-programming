package group14.polynomial

import group14.Primes
import group14.integer.ModularInteger
import group14.isPrime
import group14.superScript
import java.util.*

/**
 * @author Ruben Schellekens
 */
data class Polynomial(

        /**
         * Array containing all the coefficients of the polynomial, all integers must have the same modulus.
         *
         * The index of the coefficient is the index in the array. The end of the array may not end with 0.
         * E.g. Polynomial `X^4+2X^3-X+12` has the backing array `[12,-1,0,2,4]`.
         */
        val coefficients: Array<ModularInteger>,

        /**
         * The modulus of all coefficients.
         *
         * Must be prime.
         */
        val modulus: Long

) {

    companion object {

        /**
         * Constructs the zero polynomial with given modulus.
         */
        @JvmStatic
        fun zero(modulus: Long) = Polynomial(emptyArray(), modulus)
    }

    /**
     * Creates a polynomial with coefficients `coefficients` modulus `modulus`.
     *
     * The `i`th coefficient is the coefficient of `X^i`.
     */
    constructor(modulus: Long, vararg coefficients: Long) : this(
            coefficients.map { ModularInteger(it, modulus) }.toTypedArray(),
            modulus
    )

    init {
        // Check for trailing zeroes.
        if (coefficients.isNotEmpty()) {
            require(coefficients[coefficients.size - 1].value != 0L, { "Highest coefficient is not zero." })
        }

        // Check modulus being prime.
        if (modulus < Primes.MAX_PRIME) {
            require(modulus.isPrime(), { "Modulus must be prime, got $modulus" })
        }
    }

    /**
     * Get the degree of this polynomial.
     *
     * "The degree of a polynomial is the highest degree of its monomials with non-zero coefficients."
     * #Bless Wikipedia
     */
    val degree = coefficients.size

    /**
     * Whether it is the zero polynomial or not.
     */
    val zero = coefficients.isEmpty()

    /**
     * Checks if `a ≡ b (mod d)`.
     *
     * @param other
     *          b (other polynomial)
     * @param with
     *          d (modulus)
     * @return `true` when `other` is congruent with `this` mod `with`, `false` otherwise.
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    fun congruent(other: Polynomial, with: Polynomial): Boolean {
        // TODO: Congruence check.
        return false
    }

    /**
     * Calculates `a + b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun plus(other: ModularInteger): Polynomial {
        // TODO: Polynomial addition.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Calculates `a + b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun plus(other: Polynomial): Polynomial {
        // TODO: Polynomial addition.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Calculates `a - b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: ModularInteger): Polynomial {
        // TODO: Polynomial subtraction.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Calculates `a - b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: Polynomial): Polynomial {
        // TODO: Polynomial subtraction.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: ModularInteger): Polynomial {
        // TODO: Scalar multiplication.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: Polynomial): Polynomial {
        // TODO: Polynomial multiplication.
        return Polynomial(emptyArray(), 7)
    }

    /**
     * Performs division with remainder for `a / b`.
     *
     * @return A pair containing `(quotient, remainder)`.
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun div(other: Polynomial): Pair<Polynomial, Polynomial> {
        // TODO: Polynomial division with remainder.
        return Pair(Polynomial(emptyArray(), 7), Polynomial(emptyArray(), 7))
    }

    /**
     * Get the coefficient at a given index.
     *
     * E.g. `0` means constant, `3` means coefficient of `X^3`.
     */
    operator fun get(index: Int) = coefficients[index]

    /**
     * @return E.g. `X⁶ + 2X⁵ + 4X³ + 2X² + X + 1 (Z/5Z)...`
     */
    fun toPolynomialString(): String {
        return buildString {
            var plus = ""
            for (i in degree - 1 downTo 0) {
                val c = coefficients[i].value
                if (c == 0L) {
                    continue
                }

                append("$plus ")
                plus = "+"

                val coefficientResult = if (c == 1L && i != 0) "" else c.toString()
                when {
                    i > 1 -> append("${coefficientResult}X${superScript(i)} ")
                    i == 1 -> append("X ")
                    else -> append("$coefficientResult ")
                }
            }
            append("(Z/${modulus}Z)")
        }.trim()
    }

    /**
     * @return `coefficients (mod modulus)`
     */
    override fun toString(): String {
        return "${coefficients.contentToString()} (Z/${modulus}Z)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Polynomial) return false
        if (!Arrays.equals(coefficients, other.coefficients)) return false
        if (modulus != other.modulus) return false
        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(coefficients)
        result = 31 * result + modulus.hashCode()
        return result
    }

    fun isIrreducible(): Boolean {
        TODO("not implemented")
    }
}