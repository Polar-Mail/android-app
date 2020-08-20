package app.polarmail.settings.accounts.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.interactor.ObserveAccountInteractor
import app.polarmail.domain.interactor.RemoveAccountInteractor
import io.uniflow.core.flow.getCurrentStateOrNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountDetailSettingsViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val getAccountsInteractor: ObserveAccountInteractor,
    private val removeAccountInteractor: RemoveAccountInteractor
) : ReduxViewModel(dispatcher, AccountDetailViewState()) {

    fun load(id: Long) = viewModelScope.launch(dispatcher.main) {
        getAccountsInteractor.invoke(ObserveAccountInteractor.Params(AccountId(id)))
        getAccountsInteractor.observe()
            .flowOn(dispatcher.io)
            .collect { account ->
                action {
                    setState(
                        AccountDetailViewState(account)
                    )
                }
            }
    }

    fun logout() = action {
        val state = getCurrentStateOrNull<AccountDetailViewState>()
        state?.account?.let {
            withContext(dispatcher.io) {
                removeAccountInteractor.invoke(RemoveAccountInteractor.Params(it.id))
            }
            action {
                sendEvent(AccountDetailSettingsEvents.Logout)
            }
        }
    }

}