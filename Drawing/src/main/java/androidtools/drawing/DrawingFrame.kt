
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.View

/** Coordinate management for drawing within a Frame
  * DK96-OS : 2021 */
open class DrawingFrame {

    /** The pixel locations of the frame boundaries */
    var left: Float = 1f
    var right: Float = 1f
    var top: Float = 1f
    var bottom: Float = 1f
  
    /** Screen Pixel Density */
    var density: Float = 2f
  
    /** Additional Padding on all sides */
    var addPadding: Float = 2f
  
    /** More Frame boundary offsets */
    var leftShift: Float = 0f
    var rightShift: Float = 0f
    var bottomShift: Float = 0f
    var topShift: Float = 0f

    /** The number of pixels in the vertical dimension of the drawing area */
    val verticalPx: Float get() = bottom - top
    /** The number of pixels in the horizontal dimension of the drawing area */
    val horizontalPx: Float get() = right - left

    /** Provide additional frame boundary offsets, applied after View padding */
    open fun updateShifts(
        left: Float = 0f, right: Float = 0f, 
        bottom: Float = 0f, top: Float = 0f
    ) {
        leftShift = left
        rightShift = right
        bottomShift = bottom
        topShift = top
    }
  
    /** Retrieves Padding information from the view */
    open fun updateFrame(v: View, w: Float, h: Float) {
        updateFrame(v.resources.displayMetrics.density, w, h,
            v.paddingTop, v.paddingBottom, v.paddingStart, v.paddingEnd)
    }
  
    /** Applies specific padding parameters to setup the frame */
    open fun updateFrame(
        dens: Float, w: Float, h: Float,
        pTop: Int, pBottom: Int, pLeft: Int, pRight: Int
    ) {
        density = dens
        left = addPadding + pLeft + leftShift
        bottom = h - addPadding - pBottom - bottomShift
        top = addPadding + pTop + topShift
        right = w - addPadding - pRight - rightShift
    }
  
    /** Get Y draw coordinate from a fraction. Uses the bottom y value. */
    open fun yFraction(f: Float): Float = bottom - f * verticalPx
  
    /** Get X draw location at a fraction of the DrawingFrame. Uses the left X value. */
    open fun xFraction(f: Float): Float = left + f * horizontalPx

    companion object {
        /** Insert a line at X, where X is in the interval [0,1] */
        fun DrawingFrame.verticalLine(p: Path, x: Float) {
            val xCanvas = xFraction(x)
            p.moveTo(xCanvas, top)
            p.lineTo(xCanvas, bottom)
        }
        /** Insert a line at Y, where Y is in the interval [0,1] */
        fun DrawingFrame.horizontalLine(p: Path, y: Float) {
            val yCanvas = yFraction(y)
            p.moveTo(left, yCanvas)
            p.lineTo(right, yCanvas)
        }
        /** Draw a vertical line directly onto Canvas, where x is in the interval [0,1] */
        fun DrawingFrame.verticalLine(c: Canvas, x: Float, p: Paint) { 
            c.drawLine(x, bottom, x, top, p)
        }
        /** Draw a horizontal line directly onto Canvas, where y is in the interval [0,1] */
        fun DrawingFrame.horizontalLine(c: Canvas, y: Float, p: Paint) {
            c.drawLine(left, y, right, y, p)
        }
    }
}
