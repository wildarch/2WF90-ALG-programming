package group14

/**
 * An Either object contains an object of one of three types.
 *
 * You can construct either objects using [Left] for type `W`, [Middle] for type `T`, [Right] for type `F`.
 *
 * @author Ruben Schellekens
 */
sealed class Either<out W, out T, out F> {

    /**
     * Gets the value of the enclosed object.
     */
    fun value(): Any? = when (this) {
        is Left -> value
        is Middle -> value
        is Right -> value
    }
}

class Left<out W>(val value: W) : Either<W, Nothing, Nothing>()
class Middle<out T>(val value: T) : Either<Nothing, T, Nothing>()
class Right<out F>(val value: F) : Either<Nothing, Nothing, F>()