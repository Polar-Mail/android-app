package app.polarmail.auth.accountselector

import io.uniflow.core.flow.data.UIEvent

sealed class AccountSelectorEvents : UIEvent() {

    object OpenAddAccount : AccountSelectorEvents()

}