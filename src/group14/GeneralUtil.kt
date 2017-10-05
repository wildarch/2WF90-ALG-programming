package group14

/**
 * Checks a given assertion, and throws an IllegalStateException when false.
 */
@Throws(IllegalStateException::class)
fun assertState(assertion: Boolean, lazyMessage: () -> String?) {
    if (!assertion) {
        val message = lazyMessage()
        throw if (message == null) {
            IllegalStateException()
        }
        else {
            IllegalStateException(message)
        }
    }
}

/**
 * Checks a given assertion, and throws an IllegalStateException when false.
 */
@Throws(IllegalStateException::class)
fun assertState(assertion: Boolean) = assertState(assertion, { null })