package app.polarmail.domain.interactor

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveAccountInteractor @Inject constructor(
    private val accountRepository: AccountRepository
) : ObserveInteractor<ObserveAccountInteractor.Params, Account?>() {

    data class Params(val id: AccountId)

    override fun createObservable(params: Params): Flow<Account?> {
        return accountRepository.observeAccount(params.id.id)
    }

}