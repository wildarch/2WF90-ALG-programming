import org.junit.Test

import java.util.*
import kotlin.collections.HashSet
import kotlin.test.assertEquals

class UtilKtTest {
    @Test
    fun cartesianProduct() {
        val sets = ArrayList<Set<Int>>(Arrays.asList(
                HashSet<Int>(Arrays.asList(1, 2)),
                HashSet<Int>(Arrays.asList(3, 4, 5)),
                HashSet<Int>(Arrays.asList(6, 7, 8, 9))
        ))

        val res = group14.cartesianProduct(sets)
        val expected = HashSet<List<Int>>(Arrays.asList(
                ArrayList(Arrays.asList(1, 3, 6)),
                ArrayList(Arrays.asList(1, 3, 7)),
                ArrayList(Arrays.asList(1, 3, 8)),
                ArrayList(Arrays.asList(1, 3, 9)),

                ArrayList(Arrays.asList(1, 4, 6)),
                ArrayList(Arrays.asList(1, 4, 7)),
                ArrayList(Arrays.asList(1, 4, 8)),
                ArrayList(Arrays.asList(1, 4, 9)),

                ArrayList(Arrays.asList(1, 5, 6)),
                ArrayList(Arrays.asList(1, 5, 7)),
                ArrayList(Arrays.asList(1, 5, 8)),
                ArrayList(Arrays.asList(1, 5, 9)),


                ArrayList(Arrays.asList(2, 3, 6)),
                ArrayList(Arrays.asList(2, 3, 7)),
                ArrayList(Arrays.asList(2, 3, 8)),
                ArrayList(Arrays.asList(2, 3, 9)),

                ArrayList(Arrays.asList(2, 4, 6)),
                ArrayList(Arrays.asList(2, 4, 7)),
                ArrayList(Arrays.asList(2, 4, 8)),
                ArrayList(Arrays.asList(2, 4, 9)),

                ArrayList(Arrays.asList(2, 5, 6)),
                ArrayList(Arrays.asList(2, 5, 7)),
                ArrayList(Arrays.asList(2, 5, 8)),
                ArrayList(Arrays.asList(2, 5, 9))
        ))
        assertEquals(expected, res)
    }

}