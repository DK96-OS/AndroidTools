package androidtools.context

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
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
 * Use when starting another Activity: startActivity(intent, activity.getTransitionBundle()) */
fun Activity.getTransitionBundle()
    : Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()

/** Requests that the Soft Input Keyboard be hidden if shown */
fun Activity.hideKeyboard() {
    val ime = getSystemService(InputMethodManager::class.java)
    ime?.hideSoftInputFromWindow(
    	window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
