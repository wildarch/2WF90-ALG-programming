package group14.field

import group14.cartesianProduct
import group14.polynomial.Polynomial
import java.util.*

/**
 * A finite field in 'Z/pZ\[X]/f(X)' with p prime, and f an irreducible polynomial.
 *
 * @author Daan de Graaf
 */
data class FiniteField(

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
    private var elements: Set<Polynomial> = HashSet()

    init {
        require(polynomial.isIrreducible(), { "$polynomial is not irreducible" })
    }

    /**
     * Get all elements of the field.
     *
     * Elements are lazily computed when this function is called,
     * and then cached for future requests.
     *
     * @return All p^n elements of the field
     */
    fun getElements(): Set<Polynomial> {
        if (elements.isNotEmpty())
            return elements
        val degree = polynomial.degree
        val exponentsCombos = cartesianProduct(
                LongRange(0, degree - 1L).map {
                    LongRange(0, modulus - 1).toSet()
                }
        )
        assert(exponentsCombos.size == Math.pow(modulus.toDouble(), degree.toDouble()).toInt())
        elements = exponentsCombos.map { Polynomial(modulus, *it.toLongArray()) }.toSet()
        return elements
    }

    /**
     * Returns whether or not the given polynomial is an element of the field
     */
    fun isElement(poly: Polynomial): Boolean {
        if (poly.modulus != modulus) {
            return false
        }
        if (polynomial.degree < poly.degree) {
            return false
        }
        return true
    }

    /**
     * Adds two elements within the field
     *
     * @return (a + b) mod f, with f the irreducible polynomial of the field
     */
    fun add(a: Polynomial, b: Polynomial): Polynomial {
        require(isElement(a), {"Parameter $a is not an element of $this"})
        require(isElement(b), {"Parameter $b is not an element of $this"})
        val (_, r) = (a + b) / polynomial
        return r
    }

    /**
     * Subtracts two elements within the field
     *
     * @return (a - b) mod f, with f the irreducible polynomial of the field
     */
    fun subtract(a: Polynomial, b: Polynomial): Polynomial {
        require(isElement(a), {"Parameter $a is not an element of $this"})
        require(isElement(b), {"Parameter $b is not an element of $this"})
        val (_, r) = (a - b) / polynomial
        return r
    }

    /**
     * Multiplies two elements within the field
     *
     * @return (a * b) mod f, with f the irreducible polynomial of the field
     */
    fun multiply(a: Polynomial, b: Polynomial): Polynomial {
        require(isElement(a), {"Parameter $a is not an element of $this"})
        require(isElement(b), {"Parameter $b is not an element of $this"})
        val (_, r) = (a * b) / polynomial
        return r
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