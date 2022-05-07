package androidtools.adapters.paging

import androidtools.adapters.actionmode.KeyIndexSelectionManager
import androidx.lifecycle.Lifecycle
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

/** Adapter providing Paging3 and Item Selection
 * @author DK96-OS : 2021 */
abstract class AsyncPagingDataSelectionAdapter<
        VH : RecyclerView.ViewHolder,
        Data : Any
        >(
    diffCallback: DiffUtil.ItemCallback<Data>
)
    : RecyclerView.Adapter<VH>() {

    protected abstract fun getDataId(data: Data): Long

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

    protected val selectionMgr
        : KeyIndexSelectionManager = KeyIndexSelectionManager()

    private var listDiff: AsyncPagingDataDiffer<Data> = AsyncPagingDataDiffer(
        diffCallback = diffCallback,
        updateCallback = mListDiffCallback
    )

    override fun getItemCount()
        : Int = listDiff.itemCount

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getDataAt(position)
        if (data != null) {
            val isSelected = selectionMgr.isSelected(getDataId(data))
            bindVH(holder, position, data, isSelected)
        } else bindVHNoData(holder, position, false)
    }

    /** Bind the VH with data and selection status prefetched */
    protected abstract fun bindVH(
        holder: VH, position: Int, data: Data, isSelected: Boolean)

    /** Bind the VH when the list does not contain data at this position */
    protected open fun bindVHNoData(
        holder: VH, position: Int, isSelected: Boolean) {}

    /**  */
    fun updateData(lifecycle: Lifecycle, list: PagingData<Data>?) {
        if (list != null) listDiff.submitData(lifecycle, list)
    }

    suspend fun updateData(list: PagingData<Data>?) {
        if (list != null) listDiff.submitData(list)
    }

    /**  */
    fun getDataAt(position: Int)
            : Data? = listDiff.getItem(position)

    /**  */
    fun getDataList()
        : List<Data> = listDiff.snapshot().items

    /**  */
    fun getSelectedData(): List<Data>? {
        val selectedIds = selectionMgr.getSelected()
        return if (selectedIds.isEmpty()) null else {
            val selectedData = ArrayList<Data>(selectedIds.size)
            val dataSource = listDiff.snapshot()
            for (i in selectedIds.indices) {
                val selectedPair = selectedIds[i]
                val data = dataSource.getOrNull(selectedPair.second)
                if (data != null) {
                    if (getDataId(data) == selectedPair.first)
                        selectedData.add(data)
                    else
                        dataSource.items.find {
                            getDataId(it) == selectedPair.first
                        }?.also { selectedData.add(it) }
                } else {
                    dataSource.items.find {
                        getDataId(it) == selectedPair.first
                    }?.also { selectedData.add(it) }
                }
            }
            selectedData
        }
    }

    /** Clears any selected items. Returns true if there were items selected */
    fun clearSelectedItems(): Boolean {
        val indices = selectionMgr.getSelected()
        return if (indices.isNotEmpty()) {
            selectionMgr.clear()
            if (indices.size == 1) notifyItemChanged(indices.first().second)
            if (indices.size <= 3)
                for (i in indices.indices) notifyItemChanged(indices[i].second)
            else {
                val min = indices.minOf { it.second }
                val max = indices.maxOf { it.second }
                notifyItemRangeChanged(
                    min, 1 + max - min
                )
            }
            true
        } else false
    }
}