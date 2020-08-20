package app.polarmail.settings.accounts

import app.polarmail.core.util.AccountId
import io.uniflow.core.flow.data.UIEvent

sealed class AccountSettingsEvents : UIEvent() {

    data class OpenAccount(val id: AccountId) : AccountSettingsEvents()

    object OpenAddAccount : AccountSettingsEvents()

}