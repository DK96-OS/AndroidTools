package androidtools.adapters.diff

import androidtools.adapters.actionmode.IndexSelectionManager
import androidx.recyclerview.widget.*

/** Adapter providing AsyncDiff and Item Selection
 * @author DK96-OS : 2021 */
abstract class AsyncDiffSelectionAdapter<VH : RecyclerView.ViewHolder, Data : Any>(
    diffCallback: DiffUtil.ItemCallback<Data>,
    private val dataListLimit: Int? = null
) : RecyclerView.Adapter<VH>() {

    private val mListDiffCallback = object: ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            notifyItemRangeChanged(position, count, payload)
        }
        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }
        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }
        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }
    }

    protected val selectionMgr: IndexSelectionManager = IndexSelectionManager()

    private var listDiff: AsyncListDiffer<Data> = AsyncListDiffer(
        mListDiffCallback, AsyncDifferConfig.Builder(diffCallback).build())

    override fun getItemCount(): Int = listDiff.currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getDataAt(position)
        val isSelected = selectionMgr.checkSelected(position)
        if (data != null) bindVH(holder, position, data, isSelected)
        else bindVHNoData(holder, position, isSelected)
    }

    /** Bind the VH with data and selection status prefetched */
    protected abstract fun bindVH(
        holder: VH, position: Int, data: Data, isSelected: Boolean)

    /** Bind the VH when the list does not contain data at this position */
    protected open fun bindVHNoData(
        holder: VH, position: Int, isSelected: Boolean) {}

    /**  */
    fun updateData(list: List<Data>?) {
        if (list == null || dataListLimit == null || list.size <= dataListLimit)
            listDiff.submitList(list)
    }

    /**  */
    fun getDataAt(position: Int)
            : Data? = listDiff.currentList.getOrNull(position)

    /**  */
    fun getDataList(): List<Data> = listDiff.currentList

    /**  */
    fun getSelectedData(): List<Data>? {
        val selectedIndices = selectionMgr.getSelectedIndices()
        return if (selectedIndices.isEmpty()) null else {
            val selectedData = ArrayList<Data>(selectedIndices.size)
            val dataSource = listDiff.currentList
            for (i in selectedIndices.indices) {
                val data = dataSource.getOrNull(selectedIndices[i])
                if (data != null) selectedData.add(data)
            }
            selectedData
        }
    }

    /** Clears any selected items. Returns true if there were items selected */
    fun clearSelectedItems(): Boolean {
        val indices = selectionMgr.getSelectedIndices()
        return if (indices.isNotEmpty()) {
            selectionMgr.clear()
            for (i in indices.indices) notifyItemChanged(indices[i])
            true
        } else false
    }

}