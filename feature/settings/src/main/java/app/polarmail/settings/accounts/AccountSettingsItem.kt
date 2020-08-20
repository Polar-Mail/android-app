package app.polarmail.settings.accounts

import app.polarmail.domain.model.Account

sealed class AccountSettingsItem {

    data class AccountItem(val account: Account) : AccountSettingsItem()

    object AddAccount : AccountSettingsItem()

}