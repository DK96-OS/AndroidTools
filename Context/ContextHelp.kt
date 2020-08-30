import android.text.format.DateUtils
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/** Developed by DK96-OS : 2018 - 2020 */

/** DateUtils Format: ShowTime */
fun formatTime(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, DateUtils.FORMAT_SHOW_TIME)

/** DateUtils Format: ShowDate, AbbrevMonth, ShowTime */
fun formatDate(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, 
    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_TIME)

/** DateUtils Format: ShowDate, AbbrevMonth */
fun formatDateNoTime(ctx: Context?, time: Long)
    : String = DateUtils.formatDateTime(ctx, time, 
    DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH)
    
/** Display a Toast */
fun Context.showToast(s: String, t: Int = Toast.LENGTH_SHORT) { Toast.makeText(this, s, t).show() }

/** Requires the Fragment to be attached to a Context first
 * @param id The Drawable Resource id */
fun Fragment.getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(requireContext(), id)

/** Obtains the SceneTransition Animation Bundle for the current Activity.
 * Use when starting another Activity: startActivity(intent, activity.getTransitionBundle()) */
fun Activity.getTransitionBundle()
        : Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
