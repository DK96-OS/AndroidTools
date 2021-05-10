
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
		mQueue.forEach {it.cancel(cause)}
		mQueue.clear()
	}

	companion object {
		/** Performs a suspendable transformation function on a list using a CoroutineQueue */
		suspend fun <A, B> transformList(
			input: List<A>, 
			f: suspend (A) -> B
		): ArrayList<B>? {
			val c = CoroutineQueue<B>(input.size)
			coroutineScope {input.forEach {
				c.add(async(Dispatchers.IO) { f(it) })
			} }
			return c.awaitList()
		}
	}
}
