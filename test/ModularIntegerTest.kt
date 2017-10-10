
import group14.integer.ModularInteger
import group14.integer.mod
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
    fun `Multiplication`() {
        val random = Random(213)
        for (i in 0..100) {
            for (p in PRIMES) {
                val a = Math.abs(random.nextInt() % p).toLong()
                val b = Math.abs(random.nextInt() % p).toLong()
                val result = (a * b) % p
                val expectedResult = ModularInteger(result, p.L)
                val actualResult = ModularInteger(a, p.L) * ModularInteger(b, p.L)
                assertEquals(expectedResult, actualResult, "$a*$b (mod $p)")
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

    @Test
    fun `Inverse`() {
        val random = Random(213)
        for (i in 0..100) {
            for (p in PRIMES) {
                val int = ModularInteger(Math.abs(random.nextLong()) % p, p.L)
                if (int.value == 0L) {
                    continue
                }
                val inv = int.inverse()
                val one = int * inv
                assertEquals(ModularInteger(1, p.L), one, "$int^-1")
            }
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `Inverse Zero`() {
        (0 mod 7).inverse()
    }

    @Test
    fun `Division`() {
        val random = Random(213)
        for (i in 0..100) {
            for (p in PRIMES) {
                val a = (random.nextInt(p - 1) + 1) mod p.L
                val b = (random.nextInt(p - 1) + 1) mod p.L
                val bInv = b.inverse()
                assertEquals(a * bInv, a / b, "$a / $b")
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Division Zero`() {
        (3 mod 7) / (0 mod 7)
    }

    val Int.L
        get() = toLong()
}