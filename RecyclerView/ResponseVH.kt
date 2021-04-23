/** Developed by DK96-OS : 2018 - 2020 */

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

@Deprecated("Use a ViewHolder that supports ViewBinding")
/** Reusable ViewHolder solution for easy RecyclerView setup */
open class ResponseVH(v: View, listener: Listener): RecyclerView.ViewHolder(v) {

    /** Response Callback Interface */
    interface Listener {
        /** Handle an action sent from a ViewHolder ClickListener
         * @param index Current data position of the ViewHolder
         * @param action String sent from the ClickListener */
        fun respond(index: Int, action: String? = null) {}
    }

    private val rvhListener = WeakReference(listener)
    private val view = WeakReference(v)

    /** Returns the View if it is still available. Should not be used outside of ResponseAdapter */
    internal fun getView(): View? = view.get()

    /** Used internally by the ResponseAdapter. Is checked every onBindViewHolder call.
     * Do not modify, unless you want additional click listeners to be set */
    internal var hasViewListeners: Boolean = false

    /** Sends an action to the Listener, along with the ViewHolder's current index
     * @param ra "ResponseAction" - A key determining the intended response to a ClickListener  */
    fun action(ra: String? = null) { rvhListener.get()?.respond(adapterPosition, ra) }
}
