package app.polarmail.core_ui.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

fun Context.getAttrColor(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}