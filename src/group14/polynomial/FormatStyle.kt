import group14.Option
import group14.polynomial.Polynomial
import group14.polynomial.PolynomialStyler
import group14.superScript

/**
 * Formatting style for the elements of the table.
 */
enum class FormatStyle(override val styler: (Polynomial) -> String) : PolynomialStyler {

    /**
     * Use of pretty polynomial representation using [Polynomial.toPolynomialString].
     */
    PRETTY({ it.toPolynomialString({ superScript(it) }) { "(ℤ/${it}ℤ)" } }),

    /**
     * Use of ascii representatation
     */
    ASCII({ it.toPolynomialString({"^"+it}) { "(Z/${it}Z)" } }),

    /**
     * Use of coefficient list representation using [Polynomial.toString]
     */
    COEFFICIENT_LIST({ it.toString() });

    companion object {

        /**
         * Get the option object that corresponds to the given flag.
         */
        @JvmStatic
        fun fromOption(options: Set<Option>): FormatStyle {
            return when {
                Option.UNICODE in options -> PRETTY
                Option.COEFFICIENT_LIST in options -> COEFFICIENT_LIST
                else -> ASCII
            }
        }
    }
}