package app.polarmail.settings.accounts.detail

import io.uniflow.core.flow.data.UIEvent

sealed class AccountDetailSettingsEvents : UIEvent() {

    object Logout : AccountDetailSettingsEvents()

}