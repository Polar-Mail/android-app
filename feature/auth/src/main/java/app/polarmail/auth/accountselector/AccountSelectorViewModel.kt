package app.polarmail.auth.accountselector

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.interactor.ObserveAccountsInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AccountSelectorViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val observeAccountsInteractor: ObserveAccountsInteractor
) : ReduxViewModel(dispatcher, AccountSelectorViewState()) {

    init {
        load()
    }

    fun load() = viewModelScope.launch(dispatcher.main) {
        observeAccountsInteractor.invoke(Unit)
        observeAccountsInteractor.observe()
            .flowOn(dispatcher.io)
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

}