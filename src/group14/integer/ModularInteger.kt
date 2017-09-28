package group14.integer

import group14.Primes
import group14.isPrime

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
        // TODO: Implement addition.
        return ModularInteger(0, other.modulus)
    }

    /**
     * Calculates `a-b (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun minus(other: ModularInteger): ModularInteger {
        // TODO: Implement subtraction.
        return ModularInteger(0, other.modulus)
    }

    /**
     * Calculates `ab (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun times(other: ModularInteger): ModularInteger {
        // TODO: Implement multiplication.
        return ModularInteger(0, other.modulus)
    }

    /**
     * Calculates `ab^-1 (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    @Throws(IllegalArgumentException::class)
    operator fun div(other: ModularInteger): ModularInteger {
        // TODO: Implement division.
        return ModularInteger(0, other.modulus)
    }

    /**
     * Calculates the inverse `a^-1 (mod p)`.
     *
     * @throws IllegalArgumentException When the modulus of this integer does not equal the modulus of `other`.
     */
    fun inverse(): ModularInteger {
        // TODO: Implement inversion.
        return ModularInteger(0, modulus)
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