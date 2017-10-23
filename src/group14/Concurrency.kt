@file:JvmName("Concurrency")

package group14

import java.util.concurrent.Callable
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * An action that returns nothing.
 */
typealias Action = () -> Unit

/**
 * @author Ruben Schellekens
 */
object WorkStealingPool : ForkJoinPool(
        processorCount(),
        ForkJoinPool.defaultForkJoinWorkerThreadFactory,
        null, true
)

/**
 * @author Ruben Schellekens
 */
class TaskState<T>(val future: Future<T>) {

    fun wait() = get()
    fun wait(timeoutMillis: Long) = wait(timeoutMillis, TimeUnit.MILLISECONDS)
    fun wait(timeout: Long, unit: TimeUnit) = get(timeout, unit)
    fun get() = future.get()!!
    fun get(timeoutMillis: Long) = get(timeoutMillis, TimeUnit.MILLISECONDS)
    fun get(timeout: Long, unit: TimeUnit) = future.get(timeout, unit)!!
    fun kill() = future.cancel(true)
}

/**
 * Get the available amount of processors.
 */
fun processorCount() = Runtime.getRuntime().availableProcessors()

/**
 * Executes the given code in parallel.
 */
inline fun launch(crossinline runnable: Action) = async(runnable)

/**
 * Executes the given code in parallel.
 */
inline fun <T> async(crossinline runnable: () -> T): TaskState<T> {
    return TaskState(WorkStealingPool.submit(Callable { runnable() }))
}

/**
 * Executes multiple tasks without return values in parallel and block until all actions are done.
 */
fun chain(runnables: Collection<Action>) = chain(*runnables.toTypedArray())

/**
 * Executes multiple tasks without return values in parallel and block until all actions are done.
 */
fun chain(vararg runnables: Action) {
    val tasks = ArrayList<TaskState<Unit>>()
    for (action in runnables) {
        tasks.add(async<Unit>(action))
    }
    tasks.forEach { it.wait() }
}