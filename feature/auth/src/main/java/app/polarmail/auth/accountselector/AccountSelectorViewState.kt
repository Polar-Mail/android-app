package app.polarmail.auth.accountselector

import io.uniflow.core.flow.data.UIState

data class AccountSelectorViewState(
    val items: List<AccountSelectorItem> = emptyList()
) : UIState()