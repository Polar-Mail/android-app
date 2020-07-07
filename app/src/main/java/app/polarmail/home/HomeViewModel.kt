package app.polarmail.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.interactor.GetAccountsInteractor
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.AuthState
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel @ViewModelInject constructor(
    dispatcher: DispatcherProvider,
    accountManager: AccountManager,
    getAccountsInteractor: GetAccountsInteractor
) : ReduxViewModel(dispatcher) {

    init {
        accountManager.observeAuthState()
            .flowOn(dispatcher.io)
            .onEach { auth ->
                val authState = if (auth == AuthState.LOGGED_IN) {
                    val accounts = getAccountsInteractor.invoke(Unit)
                    HomeAuthState.LoggedIn(accounts.first { it.isSelected }, accounts)
                } else {
                    HomeAuthState.LoggedOut
                }
                action {
                    setState(HomeState(authState))
                }
            }
            .launchIn(viewModelScope)
    }

    fun logout() {
        // TODO
    }

}