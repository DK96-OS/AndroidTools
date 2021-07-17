package androidtools.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue

/** A Queue that relies on LiveData observer for processing */
class LiveDataQueue<T>(qSize: Int = 4) {

    val liveData: LiveData<Queue<T>> get() = mLiveData

    private val mLiveData = MutableLiveData<Queue<T>>()

    // Fixed size reusable queue structure
    private val mQueue: Queue<T> = ArrayBlockingQueue(qSize)

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
     *  Returns items that couldn't be queued */
    fun queueEvents(vararg e: T): List<T>? {
        var counter = 0
        while (counter < e.size && mQueue.offer(e[counter])) counter++
        repost()
        return if (counter < e.size) e.drop(counter) else null
    }

    fun clear() { mQueue.clear() }
}
