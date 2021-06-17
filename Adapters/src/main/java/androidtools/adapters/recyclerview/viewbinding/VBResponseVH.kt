package androidtools.adapter.recyclerview.viewbinding

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/** Response ViewHolder that uses ViewBinding
 * Developed by DK96-OS : 2018 - 2021 */
class VBResponseVH<Bind>(
	binding: Bind, rootView: View, listener: Listener
) : RecyclerView.ViewHolder(rootView) {

	constructor(b: Pair<Bind, View>, l: Listener) : this(b.first, b.second, l)

	/** Response Callback Interface */
	interface Listener {
		/** Handle an action sent from a ViewHolder ClickListener
		 * @param index Current data position of the ViewHolder
		 * @param action String sent from the ClickListener */
		fun respond(index: Int, action: String? = null) {}
	}

	private val binding = WeakReference(binding)
	private val aListener = WeakReference(listener)

	/** Obtain binding, required by Adapter */
	internal fun getBinding() = binding.get()

	/** Sends an action to the Listener, with the current index
	 * @param k A key for the intended response */
	fun action(k: String? = null) { aListener.get()?.respond(adapterPosition, k) }
}
