package group14.parser

import group14.integer.ModularInteger
import group14.parser.Parser.ASTNode
import group14.polynomial.Polynomial

/**
 * This abomination turns a polynomial [ASTNode] into a real [Polynomial].
 *
 * @author Ruben Schellekens
 */
open class PolynomialConverter(val node: ASTNode, val modulus: Long) {

    init {
        require(node.type == TokenType.POLYNOMIAL) {
            "ASTNode must be a polynomial, got ${node.type}"
        }
    }

    /**
     * Converts a polynomial ASTNode (both list form and paramter form) to an actual polynomial.
     *
     * @param node
     *          The polynomial node to convert. Must be of type [TokenType.POLYNOMIAL].
     */
    fun convert(): Polynomial {
        // Zero polynomial
        val elements = node.children
        if (elements.isEmpty()) {
            return Polynomial.zero(modulus)
        }

        // Convert if coefficient list.
        if (elements.any { it.type == TokenType.SEPARATOR }) {
            return fromCoefficientList()
        }

        // Convert only number.
        if (elements.all { it.type == TokenType.NUMBER }) {
            return fromNumber()
        }

        // Convert if parameter representation.
        return fromParameterRepresentation()
    }

    /**
     * Converts a number to a polynomial.
     */
    private fun fromNumber(): Polynomial {
        val number = ModularInteger.fromNode(node.children[0], modulus)
        return Polynomial(arrayOf(number), modulus)
    }

    /**
     * Converts form a coefficient list.
     */
    private fun fromCoefficientList(): Polynomial {
        val coefficients = ArrayList<ModularInteger>()
        for (i in 0 until node.children.size step 2) {
            val number = ModularInteger.fromNode(node.children[i], modulus)
            coefficients.add(number)
        }

        return Polynomial(coefficients.toTypedArray(), modulus)
    }

    /**
     * Converts from parameter representation.
     */
    private fun fromParameterRepresentation(): Polynomial {
        // Maps power=>coefficient.
        val coefficients = HashMap<Int, ModularInteger>()
        val parts = splitRepresentation()
        var maxPower = 0

        for (part in parts) {
            var coefficient = coefficients[part.power] ?: ModularInteger(0, modulus)
            coefficient += ModularInteger.reduce(Math.abs(part.coefficient) * part.operation.long, modulus)
            coefficients[part.power] = coefficient
            maxPower = Math.max(maxPower, part.power)
        }

        // Create coefficient array.
        val coefficientArray = Array<ModularInteger>(maxPower + 1) {
            coefficients[it] ?: ModularInteger(0, modulus)
        }

        return Polynomial(coefficientArray, modulus)
    }

    /**
     * Splits the polynomial in easier to process [Part]s.
     */
    fun splitRepresentation(): List<Part> {
        // Ugly code ahead... be warned. Parsing polynomials is hell.
        // I'm not proud of this. But it works eh! That's all that counts in the end, isn't it?
        val result = ArrayList<Part>()

        val initialChild: ASTNode? = node.children[0]
        val tracker = ChildTracker(initialChild!!, result, modulus)
        outer@ while (!tracker.isDone()) {
            var operation = Operation.ADD
            var coefficient = 0L

            // Checks all coefficient cases and brings the `child` variable to either the next polynomial or the
            // operation of the next child.

            // Case +-#*X
            if (tracker.type() == TokenType.NUMBER) {
                operation = tracker.child().sign()
                coefficient = tracker.child().text.toLong()
                tracker.next(operation, coefficient, 0) ?: break

                when (tracker.type()) {
                    TokenType.NUMBER, TokenType.SUBTRACT -> {
                        result.add(Part(operation, coefficient, 0))
                        continue@outer
                    }
                    TokenType.ADD -> {
                        tracker.push(operation, coefficient, 0)
                        continue@outer
                    }
                }

                if (tracker.type() == TokenType.MULTIPLY) {
                    tracker.next(operation, coefficient, 0) ?: break
                }
                if (tracker.type() != TokenType.PARAMETER) {
                    tracker.next(operation, coefficient, 0) ?: break
                }
            }
            // Case -#X
            else if (tracker.type() == TokenType.SUBTRACT) {
                if (tracker.child().next?.type == TokenType.NUMBER) {
                    coefficient = tracker.child().next!!.text.toLong()
                    tracker.next(operation, coefficient, 0) ?: break
                }
                else {
                    coefficient = -1L
                }

                operation = Operation.SUBTRACT

                if (tracker.child().next?.type == TokenType.MULTIPLY) {
                    tracker.next(operation, coefficient, 0)
                }
                if (tracker.child().next?.type != TokenType.PARAMETER) {
                    tracker.push(operation, coefficient, 0)
                    continue@outer
                }

                tracker.next(operation, coefficient, 1)
            }
            // Case X
            else if (tracker.type() == TokenType.PARAMETER) {
                coefficient = 1L
            }
            // Case +
            else if (tracker.type() == TokenType.ADD) {
                tracker.next(operation, coefficient, 0) ?: break
                coefficient = tracker.child().text.toLong()

                when (tracker.child().next?.type) {
                    TokenType.ADD, TokenType.SUBTRACT, TokenType.NUMBER -> {
                        tracker.push(operation, coefficient, 0)
                        continue@outer
                    }
                    else -> tracker.next(operation, coefficient, 0) ?: break@outer
                }
            }

            // Read parameter
            if (tracker.type() != TokenType.PARAMETER) {
                tracker.next(operation, coefficient, 0) ?: break
                continue
            }

            // Read power
            tracker.next(operation, coefficient, 1) ?: break
            when (tracker.type()) {
                TokenType.NUMBER, TokenType.ADD, TokenType.SUBTRACT -> {
                    result.add(Part(operation, coefficient, 1))
                    continue@outer
                }
            }

            check(tracker.type() == TokenType.POWER) { "Token type must be a POWER, got ${tracker.type()}" }
            tracker.next(operation, coefficient, 0) ?: break
            check(tracker.type() == TokenType.NUMBER) { "Token type must be a NUMBER, got ${tracker.type()}" }
            val power = tracker.child().text.toInt()

            // Push part.
            tracker.push(operation, coefficient, power)
        }

        return result
    }

    /**
     * Checks if the given ASTNode is a negative 'thing'.
     */
    private fun ASTNode.sign() = if (text.startsWith("-")) Operation.SUBTRACT else Operation.ADD

    /**
     * Keeps track of a currently active child.
     *
     * @author Ruben Schellekens
     */
    private class ChildTracker(initialChild: ASTNode, val parts: MutableList<Part>, val modulus: Long) {

        /**
         * The currently evaluated child.
         */
        private var child: ASTNode? = initialChild

        /**
         * Proceeds to the sibling of the active child if it exists, otherwise it adds a [Part] to the part list.
         *
         * @return `null` when there is no new sibling, `Unit` otherwise.
         */
        fun next(operation: Operation, coefficient: Long, power: Int): Unit? {
            val next = child?.next
            child = if (next != null) {
                next
            }
            else {
                val part = Part(operation, coefficient, power)
                parts.add(part)
                child = null
                return null
            }

            return Unit
        }

        /**
         * Pushes the data of the current Part and resets.
         */
        fun push(operation: Operation, coefficient: Long, power: Int): Part {
            next(operation, coefficient, power) ?: run {
                return parts.last()
            }
            val part = Part(operation, coefficient, power)
            parts.add(part)
            return part
        }

        /**
         * Get the type of the currently active node (`null` when the child is `null`).
         */
        fun type() = child?.type

        /**
         * Get the currently active child.
         */
        fun child(): ASTNode {
            assert(child != null) { "Child must not be null when calling child()!" }
            return child!!
        }

        /**
         * Checks whether all siblings have been visited, i.e. the child is `null`.
         */
        fun isDone() = child == null
    }

    /**
     * Bundles a coefficient with its parameter thingies and if it must be added, or subtracted from the previous
     * [Part].
     *
     * @author Ruben Schellekens
     */
    class Part(val operation: Operation = Operation.ADD, val coefficient: Long, val power: Int) {

        /**
         * Format: `[operation][integer]X^[power]` where  `operation` is `-` or `+`, `integer` is the
         * coefficient (e.g. `3`), `power` is the power of the parameter (e.g. `4`)
         */
        override fun toString(): String {
            return "${operation.sign}${coefficient}X^$power"
        }
    }

    /**
     * @author Ruben Schellekens
     */
    enum class Operation(val sign: String, val long: Long) {
        ADD("+", 1), SUBTRACT("-", -1)
    }
}