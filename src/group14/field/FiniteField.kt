package group14.field

import group14.polynomial.Polynomial

data class FiniteField (

        /**
         * All elements are modulo this irreducible polynomial
         */
        val polynomial: Polynomial

) {

    /**
     * All coefficients of the elements are modulo this prime
     */
    val modulus: Long = polynomial.modulus

    /**
     * All elements of this Finite Field.
     */
    private val elements: Set<Polynomial> = HashSet()

    init {
        require(polynomial.isIrreducible(), { "${polynomial} is not irreducible" })
    }

    fun getElements(): Set<Polynomial> {
        if (elements.isNotEmpty())
            return elements
        val degree = polynomial.degree
        TODO("Implement")
    }

    /**
     * TODO implement
     *
     * Addition table
     * Multiplication table
     *
     * For fields a,b:
     * Addition a+b
     * Product a*b
     * Quotient a*b^-1 (So also inverse, e.g. Algorithm 2.3.3)
     *
     * Check primitivity of field element AND determine primitive elements
     * OR
     * Test irreducibility of polynomial mod p AND produce irreducible polynomials of given degree
     */
}