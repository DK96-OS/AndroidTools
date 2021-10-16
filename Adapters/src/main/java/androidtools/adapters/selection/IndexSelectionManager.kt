package androidtools.adapters.selection

import androidx.collection.ArraySet

/** Simple Index Selection Management 
  * DK96-OS : 2021 */
class IndexSelectionManager {

    private val mSelected = ArraySet<Int>()

    /** Update this index status to be selected */
    fun select(index: Int) {
        mSelected.add(index)
    }
    
    /** Update this index status to be deselected */
    fun deselect(index: Int) {
        mSelected.remove(index)
    }
    
    /** Deselect all indices */
    fun clear() {
        mSelected.clear()   
    }
    
    /** Determine the current number of selected indices */
    fun count()
            : Int = mSelected.size
    
    /** Obtain a List containing the selected indices */
    fun getIndexList()
            : List<Int> = if (mSelected.isNotEmpty())
        mSelected.toList()
    else emptyList()
    
    /** Determine if this index is currently selected */
    fun isSelected(index: Int) {
            : Boolean = mSelected.contains(index)
}
