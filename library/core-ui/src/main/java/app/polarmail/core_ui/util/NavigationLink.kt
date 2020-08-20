package app.polarmail.core_ui.util

import android.content.Intent

object NavigationLink {
    const val SETTINGS = "app.polarmail://settings"
    const val ACCOUNT_SELECTOR = "app.polarmail://main/account-selector"

    const val HOME = "app.polarmail://home"

    const val AUTH = "app.polarmail://auth"
    const val PARAM_AUTH_IS_FRESH = "is_fresh"
    fun authIntent(isAddingNewAccount: Boolean = true) = Intent(AUTH).apply {
        putExtra(PARAM_AUTH_IS_FRESH, isAddingNewAccount)
    }
}