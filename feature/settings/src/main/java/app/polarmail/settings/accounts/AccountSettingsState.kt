package app.polarmail.settings.accounts

import io.uniflow.core.flow.data.UIState

data class AccountSettingsState(
    val accounts: List<AccountSettingsItem> = emptyList()
) : UIState()