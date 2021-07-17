package androidtools.context

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

/** Context and Activity convenience */
    
/** Display a Toast */
fun Context.showToast(s: String, t: Int = Toast.LENGTH_SHORT) { 
    Toast.makeText(this, s, t).show()
}

/** Requires the Fragment to be attached to a Context first
 * @param id The Drawable Resource id */
fun Fragment.getDrawable(@DrawableRes id: Int) 
    = ContextCompat.getDrawable(requireContext(), id)

/** Obtains the SceneTransition Animation Bundle for the current Activity.
 * Use when starting another Activity */
fun Activity.getTransitionBundle()
    : Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()

/** Requests that the Soft Input Keyboard be hidden if shown */
fun Activity.hideKeyboard() {
    val ime = getSystemService(InputMethodManager::class.java)
    ime?.hideSoftInputFromWindow(
    	window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/** Hide system navigation and status bars, with swipe to show behaviour */
fun hideSystemBars(window: Window, sceneRoot:ViewGroup) {
    val wic = WindowCompat.getInsetsController(window, sceneRoot)
    if (wic != null) {
        wic.systemBarsBehavior = WindowInsetsControllerCompat
            .BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        wic.hide(WindowInsetsCompat.Type.systemBars())
    }
}
