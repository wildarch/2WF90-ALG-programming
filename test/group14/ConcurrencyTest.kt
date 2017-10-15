package group14

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author Ruben Schellekens
 */
class ConcurrencyTest {

    @Test
    fun `Async`() {
        val processors = processorCount()
        val done = Array<Boolean>(processors, { false })

        val tasks = ArrayList<TaskState<String>>()
        for (i in 0 until processors) {
            val state = async {
                println("Async > Hello from task #$i")
                done[i] = true
                "bambi"
            }
            tasks.add(state)
        }

        tasks.forEach {
            assertEquals("bambi", it.get())
        }

        for ((index, bool) in done.withIndex()) {
            assertTrue(bool, "Task $index")
        }
    }

    @Test
    fun `Chain`() {
        val processors = processorCount()
        val done = Array<Boolean>(processors, { false })

        val tasks = ArrayList<Action>()
        for (i in 0 until processors) {
            tasks.add {
                println("Chain > Hello from task #$i")
                done[i] = true
            }
        }

        chain(tasks)

        for ((index, bool) in done.withIndex()) {
            assertTrue(bool, "Task $index")
        }
    }

    @Test
    fun `Processor Count`() {
        assertEquals(Runtime.getRuntime().availableProcessors(), processorCount(), "Processors")
    }
}