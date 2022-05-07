package androidtools.view.drawing

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/** Minimalist Drawing view abstraction
  * DK96-OS : 2021 */
abstract class DrawingView(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {

	protected open fun newPaint(vararg args: Int)
	: Paint = applyPaintArgs(Paint(), *args)

	companion object {
		const val STYLE_STROKE = 12
		const val STYLE_FILL_STROKE = 13
		const val ANTI_ALIAS = 5
		const val DITHER = 7

		protected fun applyPaintArgs(p: Paint, vararg args: Int): Paint {
			if (args.isNotEmpty()) {
				p.style = when {
					args.contains(STYLE_STROKE) -> Paint.Style.STROKE
					args.contains(STYLE_FILL_STROKE) -> Paint.Style.FILL_AND_STROKE
					else -> Paint.Style.FILL
				}
				if (args.contains(ANTI_ALIAS)) p.isAntiAlias = true
				if (args.contains(DITHER)) p.isDither = true
			}
			return p
		}
	}
}
