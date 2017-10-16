package group14.parser

import group14.parser.Parser.ASTNode
import java.util.*

/**
 * Nicely formats an ASTNode tree.
 *
 * @author Ruben Schellekens
 */
open class TreePrinter(val rootNode: ASTNode, val indent: Int = 2) {

    fun print() = buildString {
        val stack = ArrayDeque<ASTNode>()

        // Utilities.
        val printNode: (ASTNode) -> Unit = { node ->
            val parents = node.parents()
            append(" ".repeat(parents * indent))
            append(if (parents == 0) "" else "\u2514 ")
            append(node.type.name)
            append(" (")
            append(node.text)
            append(")\n")
        }

        // Initialise stack.
        stack.addFirst(rootNode)

        // Continue until all children are processed.
        while (!stack.isEmpty()) {
            val current = stack.removeFirst()
            val children = current.children
            if (children.size > 0) {
                children.reversed().forEach { stack.addFirst(it) }
            }
            printNode(current)
        }
    }

    /**
     * Returns the amount of parents of an ASTNode.
     */
    private fun ASTNode.parents(): Int {
        var node: ASTNode? = this
        var result = 0
        while (node?.parent != null) {
            result++
            node = node.parent
        }
        return result
    }
}