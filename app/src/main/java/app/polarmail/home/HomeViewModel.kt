package app.polarmail.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.manager.AccountManager
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel @ViewModelInject constructor(
    dispatcher: DispatcherProvider,
    accountManager: AccountManager
) : ReduxViewModel(dispatcher) {

    init {
        accountManager.observeAuthState()
            .flowOn(dispatcher.io)
            .onEach { auth ->
                action {
                    setState { HomeState(authState = auth) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun logout() {
        // TODO
    }

}