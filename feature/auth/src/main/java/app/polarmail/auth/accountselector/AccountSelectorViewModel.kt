package app.polarmail.auth.accountselector

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.interactor.ObserveAccountsInteractor
import app.polarmail.domain.manager.AccountManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AccountSelectorViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val observeAccountsInteractor: ObserveAccountsInteractor,
    private val accountManager: AccountManager
) : ReduxViewModel(dispatcher, AccountSelectorViewState()) {

    init {
        load()
    }

    fun load() = viewModelScope.launch(dispatcher.main) {
        observeAccountsInteractor.invoke(Unit)
        observeAccountsInteractor.observe()
            .flowOn(dispatcher.io)
            .catch { Timber.e(it) }
            .collect { accounts ->
                val items = mutableListOf<AccountSelectorItem>()
                items.run {
                    add(AccountSelectorItem.AddAccount)
                    addAll(accounts.map {
                        AccountSelectorItem.Account(
                            it.id.id,
                            it.username,
                            it.avatar,
                            it.isSelected
                        )
                    })
                    add(AccountSelectorItem.Settings)
                }

                action {
                    setState(
                        AccountSelectorViewState(items)
                    )
                }
            }
    }

    fun openAddAccount() = action {
        sendEvent(AccountSelectorEvents.OpenAddAccount)
    }

    fun selectAccount(id: Long) = viewModelScope.launch(dispatcher.main) {
        val newId = AccountId(id)
        withContext(dispatcher.io) {
            accountManager.selectAccount(newId)
        }
    }

}