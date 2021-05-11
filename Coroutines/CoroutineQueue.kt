
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.CancellationException
import kotlin.collections.ArrayList

/** Simple Queue for awaiting asynchronous coroutines
  * DK96-OS : 2021 */
class CoroutineQueue<T>(capacity: Int) {
	private val mQueue: Queue<Deferred<T>> = ArrayBlockingQueue(capacity, true)

	val count: Int get() = mQueue.size
	
	/** Add a deferred result to the Queue
	 * @return True if the queue allowed the task to be added (didn't exceed capacity) */
	fun add(task: Deferred<T>) = mQueue.offer(task)

	/** Block until next coroutine finishes, returns null if empty queue */
	suspend fun awaitNext(): T? = mQueue.poll()?.await()

	/** Await each element in the queue, add it to a list and return the list */
	suspend fun awaitList(): ArrayList<T> {
		var task: Deferred<T>? = mQueue.poll()
		val list = ArrayList<T>(mQueue.count())
		while (task != null) {
			list.add(task.await())
			task = mQueue.poll()
		}
		return list
	}

	/** Block thread until queue is empty */
	suspend fun awaitAll() {
		var task: Deferred<T>? = mQueue.poll()
		while (task != null) {
			task.await()
			task = mQueue.poll()
		}
	}

	/** Tries to cancel everything in the queue */
	fun cancel(cause: CancellationException? = null) {
		mQueue.forEach { it.cancel(cause) }
		mQueue.clear()
	}

	companion object {
		/** Applies a suspendable transformation on a list using the CoroutineQueue
		 * Skips using CoroutineQueue if input size is less than 2 */
		suspend fun <A, B> transformList(
			input: List<A>, transform: suspend (A) -> B
		): ArrayList<B> = when (input.size) {
			0 -> arrayListOf()
			1 -> arrayListOf(transform(input[0]))
			else -> {
				val c = CoroutineQueue<B>(input.size)
				coroutineScope {input.forEach {a ->
					c.add(async(Dispatchers.IO) { transform(a) })
				} }
				c.awaitList()
			}
		}
	}
}
