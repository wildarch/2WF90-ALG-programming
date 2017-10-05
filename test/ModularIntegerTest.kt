package group14.integer

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

/**
 * @author Ruben Schellekens
 */
class ModularIntegerTest {

    companion object {

        val PRIMES = setOf(11, 181, 2, 3, 5, 1819841)
    }

    @Test
    fun `Addition`() {
        val random = Random(213)
        for (i in 0..100) {
            for (p in PRIMES) {
                val a = Math.abs(random.nextInt() % p)
                val b = Math.abs(random.nextInt() % p)
                val expectedResult = ModularInteger(((a + b) % p).L, p.L)
                val actualResult = ModularInteger(a.L, p.L) + ModularInteger(b.L, p.L)
                assertEquals(expectedResult, actualResult, "$a+$b (mod $p)")
            }
        }
    }

    @Test
    fun `Subtraction`() {
        val random = Random(213)
        for (i in 0..100) {
            for (p in PRIMES) {
                val a = Math.abs(random.nextInt() % p)
                val b = Math.abs(random.nextInt() % p)
                var result = (a - b) % p
                if (result < 0) {
                    result += p
                }
                val expectedResult = ModularInteger(result.L, p.L)
                val actualResult = ModularInteger(a.L, p.L) - ModularInteger(b.L, p.L)
                assertEquals(expectedResult, actualResult, "$a-$b (mod $p)")
            }
        }
    }

    val Int.L
        get() = toLong()
}