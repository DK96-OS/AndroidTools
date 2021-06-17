package androidtools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

/** A Queue that uses a LiveData to initiate event/data handling
 * Developed by DK96-OS : 2020 - 2021 */
class LiveDataQueue<T>(qSize: Int = 3) {

	val liveData: LiveData<Queue<T>>
		get() = mLiveData
	private val mLiveData = MutableLiveData<Queue<T>>()

	private val mQueue: Deque<T> = ArrayDeque(qSize)

	/** Post the Queue to the LiveData */
	fun repost() { mLiveData.postValue(mQueue) }

	/** Queues an event */
	fun queueEvent(e: T): Boolean {
		val isQueued = mQueue.offer(e)
		return if (isQueued) {
			repost()
			true
		} else {
			if (mQueue.size > 0) repost()
			false
		}
	}

	/** Queues up events, and posts to LiveData.
	 * @return Any items that couldn't be queued (due to capacity), otherwise null */
	fun queueEvents(vararg e: T): List<T>? {
		var counter = 0
		while (counter < e.size && mQueue.offer(e[counter])) counter++
		repost()
		return if (counter < e.size) e.drop(counter) else null
	}

	fun clear() { mQueue.clear() }
}
