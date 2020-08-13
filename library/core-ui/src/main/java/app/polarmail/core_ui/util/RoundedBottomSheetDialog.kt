package app.polarmail.core_ui.util

import android.app.Dialog
import android.os.Bundle
import app.polarmail.core_ui.R
import app.polarmail.core_ui.extensions.isDarkThemeOn
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * BottomSheetDialog fragment that uses a custom
 * theme which sets a rounded background to the dialog
 * and doesn't dim the navigation bar
 */
abstract class RoundedBottomSheetDialog : BottomSheetDialogFragment() {

    override fun getTheme(): Int {
        val isDark = requireContext().isDarkThemeOn()
        return if (isDark) R.style.DarkBottomSheetDialogTheme else R.style.BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

}