package androidtools.adapters.actionmode

import androidx.collection.ArraySet

/** Maintains a list of selected indices
 * @author DK96-OS : 2021 */
class IndexSelectionManager {

    private val mSelected = ArraySet<Int>()

    fun select(index: Int) {
        mSelected.add(index)
    }
    fun delete(index: Int) {
        mSelected.remove(index)
    }
    fun clear() {
        mSelected.clear()
    }

    fun countSelectedItems()
        : Int = mSelected.size

    fun getSelectedIndices()
        : List<Int> = if (mSelected.isNotEmpty())
        mSelected.toList()
    else emptyList()

    /** Determines whether the index has been selected */
    fun checkSelected(index: Int)
        : Boolean = mSelected.contains(index)

}