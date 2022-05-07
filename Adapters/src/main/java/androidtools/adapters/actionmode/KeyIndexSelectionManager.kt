package androidtools.adapters.actionmode

import android.os.Build
import androidx.collection.ArraySet

/** Manages a selection of Long Keys
 * @author DK96-OS : 2021 */
class KeyIndexSelectionManager {

    private val mSelected = ArraySet<Pair<Long, Int>>()

    fun select(key: Long, index: Int) {
        mSelected.add(key to index)
    }

    fun removeByKey(key: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            mSelected.removeIf {
                it.first == key
            }
        else
            mSelected.filter {
                it.first == key
            }.forEach {
                mSelected.remove(it)
            }
    }

    fun removeByIndex(index: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            mSelected.removeIf {
                it.second == index
            }
        else
            mSelected.filter {
                it.second == index
            }.forEach {
                mSelected.remove(it)
            }
    }

    /** Determines whether the key has been selected */
    fun isSelected(key: Long)
            : Boolean = mSelected.find { it.first == key } != null

    /** Determines whether the index has been selected */
    fun isSelected(index: Int)
            : Boolean = mSelected.find { it.second == index } != null

    fun clear() {
        mSelected.clear()
    }

    fun count()
            : Int = mSelected.size

    /** Obtain the list of selected keys */
    fun getSelected()
            : List<Pair<Long, Int>> = if (mSelected.isNotEmpty())
        mSelected.toList() else emptyList()

}
