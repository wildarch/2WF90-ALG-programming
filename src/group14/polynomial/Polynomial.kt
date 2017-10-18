package group14.polynomial

import group14.Primes
import group14.evaluation.arithmetic.EvaluationObject
import group14.integer.ModularInteger
import group14.integer.mod
import group14.isPrime
import group14.parser.Parser.ASTNode
import group14.parser.PolynomialConverter
import group14.parser.TokenType
import group14.superScript
import java.util.*

/**
 * @author Ruben Schellekens
 */
open class Polynomial : Comparable<Polynomial>, EvaluationObject {

    companion object {

        /**
         * Constructs the zero polynomial with given modulus.
         */
        @JvmStatic
        fun zero(modulus: Long) = Polynomial(emptyArray(), modulus)

        /**
         * Converts a polynomial ASTNode (both list form and paramter form) to an actual polynomial.
         *
         * @param node
         *          The polynomial node to convert. Must be of type [TokenType.POLYNOMIAL].
         */
        @JvmStatic
        fun fromNode(node: ASTNode, modulus: Long): Polynomial {
            require(node.type == TokenType.POLYNOMIAL, { "Node is not a POLYNOMIAL, but ${node.type}" })
            return PolynomialConverter(node, modulus).convert()
        }

        /**
         * Returns a polynomial consisting of only 1 term with coefficient 1
         */
        @JvmStatic
        fun singular(modulus: Long, degree: Int): Polynomial {
            val coefficients = Array(degree + 1) { 0 mod modulus }
            coefficients[degree] = 1 mod modulus
            return Polynomial(coefficients, modulus)
        }

        /**
         * Returns a random polynomial of given degree and modulus
         */
        @JvmStatic
        fun random(modulus: Long, degree: Int, random: Random = Random()): Polynomial {
            val coefficients = Array(degree + 1) { 0 mod modulus }

            for (i in coefficients.indices) {
                if (i == coefficients.lastIndex) {
                    coefficients[i] = ModularInteger(random.nextInt((modulus - 1).toInt()).toLong() + 1.toLong(), modulus)
                }
                else {
                    coefficients[i] = ModularInteger(random.nextInt((modulus).toInt()).toLong(), modulus)
                }
            }

            return Polynomial(coefficients, modulus)
        }
    }

    /**
     * Array containing all the coefficients of the polynomial, all integers must have the same modulus.
     *
     * The index of the coefficient is the index in the array. The end of the array may not end with 0.
     * E.g. Polynomial `X^4+2X^3-X+12` has the backing array `[12,-1,0,2,4]`.
     */
    var coefficients: Array<ModularInteger> = emptyArray()
        private set(value) {
            field = value
        }

    /**
     * The modulus of all coefficients.
     *
     * Must be prime.
     */
    val modulus: Long

    constructor(coefficients: Array<ModularInteger>, modulus: Long) {

        this.modulus = modulus

        // Trim off the zeroes
        this.coefficients = coefficients;
        while (this.coefficients.isNotEmpty() && this.coefficients[this.coefficients.size - 1].value == 0L) {
            this.coefficients = Arrays.copyOfRange(this.coefficients, 0, this.coefficients.size - 1)
        }

        // Check for trailing zeroes.
        if (this.coefficients.isNotEmpty()) {
            require(this.coefficients[this.coefficients.size - 1].value != 0L, { "Highest coefficient must not be zero." })
        }

        // Check modulus being prime.
        if (modulus < Primes.MAX_PRIME) {
            require(modulus.isPrime(), { "Modulus must be prime, got $modulus" })
        }
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

    /**
     * Creates a polynomial of degree 0 with the value of `integer`.
     */
    constructor(integer: ModularInteger) : this(integer.modulus, integer.value)

    /**
     * Get the degree of this polynomial.
     *
     * "The degree of a polynomial is the highest degree of its monomials with non-zero coefficients."
     * #Bless Wikipedia
     */
    val degree: Int
        get() = Math.max(coefficients.size - 1, 0)

    /**
     * Whether it is the zero polynomial or not.
     */
    val zero: Boolean
        get() = coefficients.isEmpty()

    /**
     * Checks whether the polynomial is irreducible or not.
     *
     * @return `true` when this polynomial is irreducible, `false` otherwise.
     */
    fun isIrreducible(): Boolean {
        require(degree > 0) { "Degree must be positive for a irreducibility test" }

        // Polynomials of degree 1 are always irreducible
        if (degree == 1) {
            return true
        }

        // Execute the algorithm from the lecture notes.
        var t = 0
        do {
            t++
            val other = (Polynomial.singular(modulus, Math.pow(modulus.toDouble(), t.toDouble()).toInt()) - Polynomial(modulus, 0, 1))
            val euclid = PolynomialEuclids(this, other)
            euclid.execute()
            val gcd = euclid.gcd
        }
        while (gcd == Polynomial(modulus, 1))

        return t == degree
    }

    /**
     * Checks if `a ≡ b (mod d)`.
     *
     * @param other
     *          b (other polynomial)
     * @param with
     *          d (modulus)
     * @return `true` when `other` is congruent with `this` mod `with`, `false` otherwise.
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`,
     *                                  Or the modulus of `with` does not equal the modulus of `this`
     */
    @Throws(IllegalArgumentException::class)
    fun congruent(other: Polynomial, with: Polynomial): Boolean {
        require(modulus == other.modulus, { "Moduli not the same ($modulus vs ${other.modulus}" })
        require(modulus == with.modulus, { "Modulus of mod not the same ($modulus vs ${with.modulus})" })
        require(with.degree > 0, { "Modulus may not be a zero polynomial" })

        val (_, remainder) = ((this - other) / with)
        return remainder.zero
    }

    /**
     * Calculates `a + b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun plus(other: ModularInteger): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val newCof = this.coefficients.clone()
        if (newCof.isEmpty()) return Polynomial(modulus, other.value)
        newCof[0] += other
        return Polynomial(newCof, this.modulus)
    }

    /**
     * Calculates `a + b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun plus(other: Polynomial): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val baseCof = Array<ModularInteger>(maxOf(degree, other.degree) + 1, { ModularInteger(0, modulus) })

        for (i in coefficients.indices) {
            baseCof[i] = coefficients[i]
        }
        if (other.coefficients.isEmpty()) {
            return Polynomial(baseCof, modulus)
        }
        for (i in 0 until (Math.min(baseCof.size, other.coefficients.size))) {
            baseCof[i] += other.coefficients[i]
        }
        return Polynomial(baseCof, this.modulus)
    }

    /**
     * Calculates `a - b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: ModularInteger): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val baseCof = this.coefficients.clone()
        if (baseCof.isEmpty()) return Polynomial(modulus, ModularInteger.reduce(-other.value,modulus).value)
        baseCof[0] -= other
        return Polynomial(baseCof, this.modulus)
    }

    /**
     * Calculates `a - b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: Polynomial): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val baseCof = Array<ModularInteger>(maxOf(degree, other.degree) + 1, { ModularInteger(0, modulus) })
        for (i in coefficients.indices) {
            baseCof[i] = coefficients[i]
        }
        if (other.coefficients.isEmpty()) {
            return Polynomial(baseCof, modulus)
        }
        for (i in 0 until (Math.min(baseCof.size, other.coefficients.size))) {
            baseCof[i] -= other.coefficients[i]
        }
        return Polynomial(baseCof, this.modulus)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: ModularInteger): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val baseCof = coefficients.clone()
        for (i in baseCof.indices) {
            baseCof[i] *= other;
        }
        return Polynomial(baseCof, this.modulus)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: Polynomial): Polynomial {
        require(modulus == other.modulus, { "Moduli not the same" })
        val newCof = Array<ModularInteger>(degree + other.degree + 1, { ModularInteger(0, modulus) })
        for (i in coefficients.indices) {
            for (j in other.coefficients.indices) {
                newCof[i + j] += (coefficients[i] * other.coefficients[j])
            }
        }

        return Polynomial(newCof, modulus)
    }

    /**
     * Performs division with remainder for `a / b`.
     *
     * @return A pair containing `(quotient, remainder)`.
     * @throws IllegalArgumentException When the modulus of this polynomial does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun div(other: Polynomial): Pair<Polynomial, Polynomial> {
        require(modulus == other.modulus, { "Moduli not the same" })
        require(!other.zero, { "You can not devide by zero" })

        var dif = degree - other.degree
        if (dif < 0) return Pair(Polynomial.zero(modulus), Polynomial(coefficients, modulus))

        val newCof = Array<ModularInteger>(dif + 1, { ModularInteger(0, modulus) })
        var quotient = Polynomial.zero(modulus)
        var remainder = Polynomial(coefficients, modulus)

        while (dif >= 0) {
            if (remainder.zero) break;
            newCof[dif] = remainder.coefficients[remainder.degree] * other.coefficients[other.degree].inverse()
            quotient = Polynomial(newCof, modulus)
            remainder = this - other * quotient

            dif = remainder.degree - other.degree
        }

        return Pair(quotient, remainder)
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
    @Suppress("RemoveCurlyBracesFromTemplate")
    fun toPolynomialString() = toPolynomialString {
        "(ℤ/${it}ℤ)"
    }

    /**
     * @return E.g. `X⁶ + 2X⁵ + 4X³ + 2X² + X + 1 (Z/5Z)...`
     */
    fun toPolynomialString(modulusMessage: (Long) -> String): String {
        return buildString {
            val modMessage = modulusMessage(modulus)
            if (coefficients.isEmpty()) {
                append("0")
                if (modMessage.isNotEmpty()) {
                    append(" ")
                }
                append(modMessage)
                return@buildString
            }

            var plus = ""
            for (i in degree downTo 0) {
                if (coefficients.isEmpty()) {
                    append("0 ")
                    break
                }
                val c = coefficients[i].value
                if (c == 0L) {
                    continue
                }

                append("$plus ")
                plus = "+"

                val coefficientResult = if (c == 1L && i != 0) "" else c.toString()
                when {
                    i > 1 -> append("${coefficientResult}X${superScript(i)} ")
                    i == 1 -> append("${coefficientResult}X ")
                    else -> append("$coefficientResult ")
                }
            }

            if (modMessage.isEmpty()) {
                trimEnd()
            }
            append(modulusMessage(modulus))
        }.trim()
    }

    override fun compareTo(other: Polynomial): Int {
        var compared = coefficients.size.compareTo(other.coefficients.size)
        if (compared != 0) {
            return compared
        }

        for ((a, b) in coefficients.zip(other.coefficients).reversed()) {
            compared = a.value.compareTo(b.value)
            if (compared != 0) {
                return compared
            }
        }

        return 0
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
}