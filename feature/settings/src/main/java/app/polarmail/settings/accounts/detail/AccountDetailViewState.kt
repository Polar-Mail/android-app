package app.polarmail.settings.accounts.detail

import app.polarmail.domain.model.Account
import io.uniflow.core.flow.data.UIState

data class AccountDetailViewState(
    val account: Account? = null
) : UIState()