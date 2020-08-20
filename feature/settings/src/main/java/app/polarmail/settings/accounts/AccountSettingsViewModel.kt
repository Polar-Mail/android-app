package app.polarmail.settings.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.interactor.ObserveAccountsInteractor
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber

class AccountSettingsViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val observeAccountsInteractor: ObserveAccountsInteractor
) : ReduxViewModel(dispatcher, AccountSettingsState()) {

    init {
        load()
    }

    fun load() = viewModelScope.launch(dispatcher.main) {
        observeAccountsInteractor.invoke(Unit)
        observeAccountsInteractor.observe()
            .flowOn(dispatcher.io)
            .catch {
                Timber.e(it)
            }
            .collect { accounts ->
                val mappedAccounts = accounts.map { AccountSettingsItem.AccountItem(it) }
                val items = mutableListOf<AccountSettingsItem>()
                items.addAll(mappedAccounts)
                items.add(AccountSettingsItem.AddAccount)
                action { setState(AccountSettingsState(items)) }
            }
    }

    fun goToAccount(id: AccountId) = action {
        sendEvent(AccountSettingsEvents.OpenAccount(id))
    }

    fun goToAddAccount() = action {
        sendEvent(AccountSettingsEvents.OpenAddAccount)
    }

}