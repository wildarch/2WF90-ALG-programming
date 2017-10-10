package group14.parser

import group14.parser.TokenType.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Ruben Schellekens
 */
class Parser(val lexer: Lexer) {

    /**
     * Stack containing the tokens that are being handled.
     *
     * The top element is the currently active node.
     */
    private val stack = ArrayDeque<ASTNode>()

    /**
     * Stack containing the text buffer per node.
     *
     * The top element is the buffer of the currently active node.
     * Everything must match up fine with [stack].
     */
    private val buffer = ArrayDeque<StringBuilder>()

    /**
     * Constructs the parse tree from the given lexer.
     *
     * @return The root node of the parse tree.
     * @throws ParseException When something went wrong while parsing.
     */
    @Throws(ParseException::class)
    fun constructParseTree(): ASTNode {
        // Construct root.
        val root = ASTNode(null, "", TokenType.ROOT, null, null)
        stack.addFirst(root)
        buffer.addFirst(StringBuilder())

        // Start parsing.
        parse()

        return root
    }

    /**
     * Advances to the next token.
     */
    private fun next() = lexer.nextIgnoreWhitespace()

    /**
     * Creates a new child of the currently active node and opens a new block.
     */
    private fun pushBlock(type: TokenType) {
        val parent = stack.first
        val current = lexer.current()
        val lastChild = if (parent.children.isEmpty()) null else parent.children.last()
        val child = ASTNode(parent, current.value, type, lastChild, null)
        stack.addFirst(child)
        buffer.addFirst(StringBuilder())
        parent.children.add(child)
        lastChild?.next = child
    }

    /**
     * Creates a new child of the currently active node.
     */
    private fun pushChild(type: TokenType) {
        val token = lexer.current()
        val parent = stack.first
        val children = parent.children
        val previous = if (children.isEmpty()) null else children.last()
        val child = ASTNode(parent, token.value, type, previous, null)
        buffer.first.append(lexer.whitespaceBuffer + token.value)
        parent.children.add(child)
        previous?.next = child
    }

    /**
     * Finishes off the current block and goes back up 1 level.
     */
    private fun finaliseBlock() {
        pushChild(lexer.current().type)
        val text = buffer.removeFirst().toString()
        stack.first.text += text
        stack.removeFirst()
        buffer.first.append(lexer.whitespaceBuffer + text)
    }

    /**
     * Start parsing, requires that the root is on the stack.
     */
    private fun parse() {
        var next = next()
        while (next != null) {
            when (next.type) {
                is Operator -> operator()
                OPENBRACKET -> polynomial()
                OPENPARENTHESIS -> modulus()
                else -> return
            }
            next = next()
        }
    }

    /**
     * Parse polynomials.
     */
    private fun polynomial() {
        pushBlock(POLYNOMIAL)
        var next = next()
        if (next == null) {
            error("Illegal polynomial definition")
        }

        // True when at least 1 seperator has been consumed.
        var separator: Boolean = false

        // True when at least 1 parameter has been consumed.
        var parameter: Boolean = false

        while (next != null) {
            when (next.type) {
                NUMBER -> {
                }
                SEPARATOR -> {
                    if (parameter) {
                        error("Coefficient list syntax is not allowed in parameter-style definition")
                    }
                    separator = true
                }
                PARAMETER -> {
                    if (separator) {
                        error("Parameters are not allowed in coefficient list-style definition")
                    }
                    parameter = true
                }
                is Operator -> {
                    if (separator) {
                        error("Operators are not allowed in coefficient list-style definition")
                    }

                    checkInvalidOperators()
                }
                CLOSEBRACKET -> {
                    finaliseBlock()
                    return
                }
                else -> error("Unexpected token")
            }

            pushChild(next.type)
            next = next()
        }
    }

    /**
     * Parse modulus.
     */
    private fun modulus() {
        pushBlock(MODULUS)
        var next = next()
        if (next == null) {
            error("Illegal modulus definition, (mod p) expected")
        }

        when (next?.type) {
            MODKEYWORD, NUMBER -> {
                pushChild(next.type)
                next = next()
            }
            else -> error("Expected 'mod' or a prime number")
        }

        while (next != null) {
            when (next.type) {
                MODKEYWORD, NUMBER -> {
                    pushChild(next.type)
                    next = next()
                }
                CLOSEPARENTHESIS -> {
                    finaliseBlock()
                    return
                }
                else -> error("Expected 'mod' or a prime number")
            }
        }
    }

    /**
     * Parse operator.
     */
    private fun operator() {
        checkInvalidOperators()
        pushChild(lexer.current().type)
    }

    /**
     * Checks if two operators aren't used consecutively.
     *
     * Throws an error when problems are found.
     */
    private fun checkInvalidOperators() {
        if (lexer.lexedTokens() > 1) {
            val behind = lexer.lookBehindNoWhitespace().type.javaClass.superclass
            val current = lexer.current().type.javaClass.superclass
            if (behind == current) {
                error("Cannot have two consecutive operators")
            }
        }
    }

    /**
     * Throws a parse exception.
     */
    @Throws(ParseException::class)
    private fun error(message: String) {
        val item = lexer.current()
        throw ParseException("$message, got <${item.value}> (col:${lexer.column()}).")
    }

    /**
     * A node in the abstract syntax tree.
     *
     * @author Ruben Schellekens
     */
    inner class ASTNode {

        /**
         * A list of all children of this node.
         */
        var parent: ASTNode? = null

        /**
         * The next sibling of this node or `null` when there is no next sibling.
         */
        var text: String

        /**
         * The previous sibling of this node or `null` when there is no previous sibling.
         */
        var type: TokenType

        /**
         * The type of token this node is.
         */
        var previous: ASTNode?

        /**
         * The text contained in the current node.
         */
        var next: ASTNode?

        /**
         * The parent of the current node.
         *
         * A parent of `null`, means it is the root node.
         */
        var children: MutableList<ASTNode>

        constructor(parent: ASTNode?, text: String, type: TokenType, previous: ASTNode?, next: ASTNode?, children: MutableList<ASTNode> = ArrayList()) {
            this.parent = parent
            this.text = text
            this.type = type
            this.previous = previous
            this.next = next
            this.children = children
        }

        /**
         * If the node is a root node or not.
         */
        val isRoot: Boolean
            get() = parent == null

        override fun toString(): String {
            return "($type, text: '$text', next: ${next?.type}, prev: ${previous?.type}, children: [${children.joinToString(";")}])"
        }
    }
}