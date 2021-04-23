import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

@Deprecated("Relies on ResponseVH, which is now deprecated",
           level = DeprecationLevel.WARNING)
/** A RecyclerView Adapter framework for producing responsive lists of Views
 * Developed by DK96-OS : 2020 */
abstract class PagingResponseAdapter<Data>(
    /** The Layout Resource Id to inflate for every ViewHolder */
    @LayoutRes protected val layoutId: Int,
    /** Diff Util for Data */
    diffUtil: DiffUtil.ItemCallback<Data>,
    /** Sets a ClickListener on the Layout's root view */
    protected val automaticViewAction: Boolean = true
) : PagedListAdapter<Data, ResponseVH>(diffUtil), ResponseVH.Listener {

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
}
