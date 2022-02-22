package androidtools.adapters.diff

import androidx.recyclerview.widget.*

/** 
 * @author DK96-OS : 2021 */
abstract class AsyncDiffAdapter<VH : RecyclerView.ViewHolder, Data : Any>(
        diffCallback: DiffUtil.ItemCallback<Data>,
        private val dataListLimit: Int? = 300
)
    : RecyclerView.Adapter<VH>() {

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

    private var listDiff: AsyncListDiffer<Data> = AsyncListDiffer(
            mListDiffCallback,
            AsyncDifferConfig.Builder(diffCallback).build()
    )

    override fun getItemCount(): Int = listDiff.currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getDataAt(position)
        if (data != null) bindVH(holder, position, data)
        else bindVHNoData(holder, position)
    }

    /** Bind the VH with data and selection status prefetched */
    protected abstract fun bindVH(holder: VH, position: Int, data: Data)

    /** Bind the VH when the list does not contain data at this position */
    protected open fun bindVHNoData(holder: VH, position: Int) {}

    /**  */
    fun updateData(list: List<Data>?) {
        if (list == null || dataListLimit == null || list.size <= dataListLimit)
            listDiff.submitList(list)
    }

    /**  */
    fun getDataAt(position: Int)
            : Data? = listDiff.currentList.getOrNull(position)

    /**  */
    fun getDataList()
            : List<Data> = listDiff.currentList
}
