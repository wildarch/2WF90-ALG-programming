package group14

/**
 * @author Ruben Schellekens
 */
enum class Option(val flag: String) {

    /**
     * Shows stack traces in the console.
     */
    SHOW_STACKTRACE("--show-errors"),

    /**
     * Polynomials get printed in coefficient list style.
     */
    COEFFICIENT_LIST("--coefficient-list");

    companion object {

        /**
         * Get the option object that corresponds to the given flag.
         */
        @JvmStatic
        fun fromFlag(flag: String): Option? {
            for (value in values()) {
                if (value.flag == flag) {
                    return value
                }
            }

            return null
        }
    }
}