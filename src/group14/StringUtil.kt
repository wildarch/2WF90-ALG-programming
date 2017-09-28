@file:JvmName("StringUtil")

package group14

/**
 * All superscript symbols
 */
private val SUPERSCRIPT = "⁰¹²³⁴⁵⁶⁷⁸⁹".toCharArray()

/**
 * @author Ruben Schellekens
 */
fun superScript(number: Number) = number.toLong().toString()
        .toCharArray()
        .map { SUPERSCRIPT[Integer.parseInt(it.toString())] }
        .joinToString()