/** Developed by DK96-OS : 2018 - 2020 */

import android.util.SparseBooleanArray
import androidx.core.util.forEach
import androidx.core.util.isEmpty
import java.lang.ref.WeakReference

/** Contains a list of selected items, manages updates, and notifies a listener */
class ListItemSelectionManager(listener: Listener) {

    interface Listener {
        /** Notify that this item's status has changed */
        fun notifyItemChanged(index: Int)
    }

    private val listener = WeakReference(listener)
    private val selectedItems = SparseBooleanArray()

    /** Creates an ArrayList of all indices that are currently selected */
    fun getSelectedIndices() = ArrayList<Int>().apply {
        selectedItems.forEach { index, value -> if (value) add(index) }
    }

    /** Clears all selected indices, updating the listener for each selected item */
    fun clear() {
        val l = listener.get()
        if (l != null) selectedItems.forEach { index, isSelected ->
            if (isSelected) l.notifyItemChanged(index)
        }
        if (selectedItems.size() > 0) selectedItems.clear()
    }

    /** Determines the selection status of the given index */
    fun checkSelectionFor(index: Int): Boolean = selectedItems.get(index)

    /** Select or deselect the given index */
    fun selectIndex(index: Int) {
        when {
            selectedItems.isEmpty() -> selectedItems.append(index, true)
            !checkSelectionFor(index) -> selectedItems.put(index, true)
            else -> selectedItems.delete(index)
        }
        listener.get()?.notifyItemChanged(index)
    }

}