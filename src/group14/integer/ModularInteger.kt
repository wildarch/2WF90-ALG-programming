package group14.integer

import group14.Primes
import group14.assertState
import group14.isPrime
import group14.parser.Parser
import group14.parser.TokenType

/**
 * An integer in `Z/pZ` where `p` is prime.
 *
 * @author Ruben Schellekens
 */
data class ModularInteger(

        /**
         * Literally just the value of the integer, modulo [modulus].
         *
         * The value is always in `{0,...,modulus-1}`.
         */
        val value: Long,

        /**
         * The modulus of the integer.
         *
         * Must be prime.
         */
        val modulus: Long

) : Number() {

    companion object {

        /**
         * Converts a number ASTNode to an actual modular integer.
         *
         * @param node
         *          The number node to convert. Must be of type [TokenType.NUMBER].
         */
        @JvmStatic
        fun fromNode(node: Parser.ASTNode, modulus: Long): ModularInteger {
            require(node.type == TokenType.NUMBER, { "Node is not a NUMBER, but ${node.type}" })

            val value = node.text.toLong()
            val times = value / modulus
            val reduced = value - (times - if (times < 0) 1 else 0) * modulus
            return ModularInteger(reduced, modulus)
        }
    }

    /**
     * Whether the integer is zero or not.
     */
    val zero = value == 0L

    init {
        require(modulus > 1, { "Modulus must be greater than 1, got $modulus" })
        require(value in 0 until modulus, { "Value must be in {0,1,...,modulus-1}, got $value" })

        // Check modulus being prime.
        if (modulus < Primes.MAX_PRIME) {
            require(modulus.isPrime(), { "Modulus must be prime, got $modulus" })
        }
    }

    /**
     * Calculates `a+b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun plus(other: ModularInteger): ModularInteger {
        require(modulus == other.modulus, { "Moduli are not equal: <$modulus> and <${other.modulus}>" })

        var result = value + other.value
        if (result >= modulus) {
            result -= modulus
        }

        return ModularInteger(result, modulus)
    }

    /**
     * Calculates `a-b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: ModularInteger): ModularInteger {
        require(modulus == other.modulus, { "Moduli are not equal: <$modulus> and <${other.modulus}>" })

        var result = value - other.value
        if (result < 0) {
            result += modulus
        }

        return ModularInteger(result, modulus)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: ModularInteger): ModularInteger {
        require(modulus == other.modulus, { "Moduli are not equal: <$modulus> and <${other.modulus}>" })

        val result = value * other.value
        val remainder = result % modulus

        return ModularInteger(remainder, other.modulus)
    }

    /**
     * Calculates `ab^-1 (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other` or the
     *              other element is zero.
     */
    @Throws(IllegalArgumentException::class)
    operator fun div(other: ModularInteger): ModularInteger {
        require(modulus == other.modulus, { "Moduli are not equal: <$modulus> and <${other.modulus}>" })
        require(!other.zero, { "Cannot divide by zero: other element is zero" })

        return this * other.inverse()
    }

    /**
     * Calculates the inverse `a^-1 (mod p)`.
     *
     * @throws IllegalStateException When this integer is zero.
     */
    fun inverse(): ModularInteger {
        assertState(value != 0L, { "Cannot invert 0" })

        val euclid = IntegerEuclids(value, modulus)
        val (x, _, _) = euclid.execute()

        return if (x < 0) {
            ModularInteger(x + modulus, modulus)
        }
        else {
            ModularInteger(x, modulus)
        }
    }

    /**
     * @return `value`
     */
    override fun toString(): String {
        return value.toString()
    }

    override fun toByte() = value.toByte()

    override fun toChar() = value.toChar()

    override fun toDouble() = value.toDouble()

    override fun toInt() = value.toInt()

    override fun toFloat() = value.toFloat()

    override fun toLong() = value

    override fun toShort() = value.toShort()
}

/**
 * Creates a modular integer with `this` value and a given modulus.
 */
infix fun Int.modulo(modulus: Long) = ModularInteger(toLong(), modulus)

/**
 * Creates a modular integer with `this` value and a given modulus.
 */
infix fun Long.modulo(modulus: Long) = ModularInteger(this, modulus)

/**
 * Creates a modular integer with `this` value and a given modulus.
 */
infix fun Int.mod(modulus: Long) = this modulo modulus

/**
 * Creates a modular integer with `this` value and a given modulus.
 */
infix fun Long.mod(modulus: Long) = this modulo modulus

/**
 * Calculates `this` * `other`.
 */
operator fun ModularInteger.invoke(other: ModularInteger) = this * other