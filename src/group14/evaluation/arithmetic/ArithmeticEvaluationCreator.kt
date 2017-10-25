package group14.evaluation.arithmetic

import group14.Primes
import group14.evaluation.EvaluationException
import group14.evaluation.EvaluationState
import group14.field.FiniteField
import group14.integer.ModularInteger
import group14.parser.Parser.ASTNode
import group14.parser.TokenType
import group14.polynomial.Polynomial

/**
 * @author Ruben Schellekens
 */
open class ArithmeticEvaluationCreator(val tree: ASTNode, val state: EvaluationState) {

    /**
     * Creates an evaluation from the given tree.
     *
     * @throws EvaluationException when there was a problem converting the tree.
     */
    @Throws(EvaluationException::class)
    fun create(): ArithmeticEvaluation? {
        scanMeta()

        // Check if modulo was defined.
        if (state.modulus == null) {
            throw EvaluationException("No coefficient modulus was defined")
        }

        return createEvaluation(tree.children)
    }

    private fun createEvaluation(children: List<ASTNode>): ArithmeticEvaluation? {
        // Evaluation stack.
        val stack = ArrayList<EvaluationObject>(children.size)

        outer@ for (child in children) {
            when (child.type) {
                TokenType.NUMBER -> stack.add(ModularInteger.fromNode(child, state.modulus!!))
                TokenType.POLYNOMIAL -> stack.add(Polynomial.fromNode(child, state.modulus!!))
                TokenType.PREVIOUS -> {
                    val value = state.result?.value() ?: continue@outer
                    if (value is Polynomial || value is ModularInteger) {
                        stack.add(value as EvaluationObject)
                    }
                }
                TokenType.GROUP -> {
                    val evaluation = createEvaluation(child.children) ?: continue@outer
                    stack.add(evaluation)
                }
                is EvaluationObject -> stack.add(child.type as EvaluationObject)
            }
        }

        return if (stack.isEmpty()) null else ArithmeticEvaluation(state, stack)
    }

    private fun scanMeta() {
        scanMod()
        scanPolynomialMod()
        scanField()
    }

    private fun scanField() {
        for (element in tree.children) {
            if (element.type != TokenType.META || element.children[0].type != TokenType.FIELDKEYWORD) {
                continue
            }

            val value = element.children[1]
            when (value.type) {
                TokenType.POLYNOMIAL -> {
                    if (state.modulus == null) {
                        throw EvaluationException("Coefficient modulus must be defined")
                    }

                    val polynomial = Polynomial.fromNode(value, state.modulus!!)
                    try {
                        state.field = FiniteField(polynomial)
                    } catch (illegalArgument: IllegalArgumentException) {
                        throw EvaluationException("$polynomial is reducible", illegalArgument)
                    }
                }
            }
        }
    }

    private fun scanMod() {
        treeloop@ for (element in tree.children) {
            if (element.type != TokenType.META || element.children[0].type != TokenType.MODKEYWORD) {
                continue
            }

            val value = element.children[1]
            when (value.type) {
                TokenType.NUMBER -> {
                    try {
                        val prime = value.text.toLong()
                        if (!Primes.isPrimeNumber(prime)) {
                            throw EvaluationException("$prime is not a prime number")
                        }
                        state.modulus = prime
                    } catch (numberFormatException: NumberFormatException) {
                        throw EvaluationException("Could not parse ${value.text}")
                    }
                }
                TokenType.POLYNOMIAL -> {
                    continue@treeloop
                }
                else -> throw EvaluationException("Expected NUMBER or POLYNOMIAL in mod meta, got ${value.type}")
            }
        }
    }

    private fun scanPolynomialMod() {
        treeloop@ for (element in tree.children) {
            if (element.type != TokenType.META || element.children[0].type != TokenType.MODKEYWORD) {
                continue
            }

            val value = element.children[1]
            when (value.type) {
                TokenType.NUMBER -> {
                    continue@treeloop
                }
                TokenType.POLYNOMIAL -> {
                    if (state.modulus == null) {
                        throw EvaluationException("Coefficient modulus must be defined")
                    }
                    state.polynomialModulus = Polynomial.fromNode(value, state.modulus!!)
                }
                else -> throw EvaluationException("Expected NUMBER or POLYNOMIAL in mod meta, got ${value.type}")
            }
        }
    }
}