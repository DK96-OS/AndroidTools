/** Developed by DK96-OS : 2018 - 2020 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

@Deprecated("Use an adapter that supports ViewBinding", 
            level = DeprecationLevel.WARNING)
/** A RecyclerView Adapter framework for producing responsive lists of Views */
abstract class ResponseAdapter(
    /** The Layout Resource Id to inflate for every ViewHolder */
    @LayoutRes protected val layoutId: Int,
    /** Sets a ClickListener on the Layout's root view */
    protected val automaticViewAction: Boolean = true
) : RecyclerView.Adapter<ResponseVH>(), ResponseVH.Listener {

    /** Initializes View Listeners once per ResponseVH.
     * Override this method to set your own View Listeners in the layout
     * @param v The ViewHolder Layout's root view
     * @param vh The ViewHolder itself. Call action() with optional string argument */
    open fun initViewListeners(v: View, vh: ResponseVH) {
        if (automaticViewAction) v.setOnClickListener { vh.action() }
    }

    /** Update the view with data at the given index
     * @param v The ViewHolder layout's root view
     * @param index The data index to bind the view to */
    abstract fun bindView(v: View, index: Int)

    /** Creates a ResponseVH using the layoutId, and this as the Listener */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : ResponseVH = ResponseVH(LayoutInflater.from(parent.context).inflate(
        layoutId, parent, false), this)

    /** Manages ViewHolder listener initialization and data binding */
    override fun onBindViewHolder(holder: ResponseVH, position: Int) {
        holder.getView()?.also { v ->
            if (!holder.hasViewListeners) {
                initViewListeners(v, holder)
                holder.hasViewListeners = true
            }
            bindView(v, position)
        }
    }

    companion object {
        /** Safely obtain data from a nullable Array.
         * @param index The index of the data
         * @param array A potentially nullable or empty array
         * @return Either the data in the array if everything was valid, or null */
        fun <T> getFromArray(index: Int, array: Array<T>?)
                : T? = if (array != null && index in array.indices) array[index] else null

        /** Safely obtain data from a nullable List.
         * @param index The index of the data
         * @param list A potentially nullable or empty list
         * @return Either the data in the list if everything was valid, or null */
        fun <T> getFromList(index: Int, list: List<T>?)
                : T? = if (list != null && index in list.indices) list[index] else null
    }
}
