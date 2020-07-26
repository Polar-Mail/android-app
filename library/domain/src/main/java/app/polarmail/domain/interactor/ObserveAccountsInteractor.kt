package app.polarmail.domain.interactor

import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class ObserveAccountsInteractor(
    private val accountRepository: AccountRepository
) : ObserveInteractor<Unit, List<Account>>() {

    override fun createObservable(params: Unit): Flow<List<Account>> {
        return accountRepository.observeAccounts()
    }

}