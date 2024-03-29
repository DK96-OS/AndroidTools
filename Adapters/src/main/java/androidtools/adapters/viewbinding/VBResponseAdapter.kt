package androidtools.adapters.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/** Adapter with ViewBinding support
 * @author DK96-OS : 2018 - 2021 */
abstract class VBResponseAdapter<Bind>(
	private val automaticAction: Boolean = false
) : RecyclerView.Adapter<VBResponseVH<Bind>>(), VBResponseVH.Listener {

	/** Inflate the View with a Binding.
	 * Apply any one-time-set-and-forget click listeners
	 * @param i The system provided LayoutInflater
	 * @param p The parent ViewGroup of the new ViewHolder
	 * @return ViewBinding instance and it's root View */
	protected abstract fun bindRoot(i: LayoutInflater, p: ViewGroup)
	: Pair<Bind, View>

	/** Update the view with data at the given index
	 * @param b The ViewBinding instance associated with the VH
	 * @param index The adapter index to use with the data source */
	protected abstract fun bindView(b: Bind, index: Int)

	/** Applies the bindRoot function and onClick if automaticAction is true */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
	: VBResponseVH<Bind> = if (!automaticAction)
		VBResponseVH(bindRoot(LayoutInflater.from(parent.context), parent), this)
	else {
		val (b, v) = bindRoot(LayoutInflater.from(parent.context), parent)
		val vh = VBResponseVH(b, v, this)
		v.setOnClickListener { respond(vh.bindingAdapterPosition) }
		vh
	}

	override fun onBindViewHolder(holder: VBResponseVH<Bind>, position: Int) {
		holder.getBinding()?.also { b -> bindView(b, position) }
	}

}