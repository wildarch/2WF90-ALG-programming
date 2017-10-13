package group14.integer
import group14.parser.Parser.ASTNode
import group14.parser.TokenType
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * @author Ruben Schellekens
 */
class ModularIntegerTest {

    companion object {

        val PRIMES = setOf(11, 181, 2, 3, 5, 1819841)
    }

    @Test
    fun `From AST`() {
        val modulus = 17L

        TokenType.values()
        val node0 = ASTNode(null, "14", TokenType.NUMBER, null, null)
        val int0 = ModularInteger(14, modulus)
        val node1 = ASTNode(null, "123456789123456789", TokenType.NUMBER, null, null)
        val int1 = ModularInteger(8, modulus)
        val node2 = ASTNode(null, "-123", TokenType.NUMBER, null, null)
        val int2 = ModularInteger(13, modulus)
        val node3 = ASTNode(null, "0", TokenType.NUMBER, null, null)
        val int3 = ModularInteger(0, modulus)
        val node4 = ASTNode(null, "16", TokenType.KEYWORD, null, null)

        assertEquals(int0, ModularInteger.fromNode(node0, modulus), "14 (mod 17)")
        assertEquals(int1, ModularInteger.fromNode(node1, modulus), "123456789123456789 (mod 17)")
        assertEquals(int2, ModularInteger.fromNode(node2, modulus), "-123 (mod 17)")
        assertEquals(int3, ModularInteger.fromNode(node3, modulus), "0 (mod 17)")
        assertFailsWith(IllegalArgumentException::class) {
            ModularInteger.fromNode(node4, modulus)
        }
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