package app.polarmail.domain.interactor

import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccountsInteractor @Inject constructor(
    private val accountRepository: AccountRepository
) : ResultInteractor<Unit, List<Account>>() {

    override suspend fun doWork(params: Unit): List<Account> {
        return accountRepository.getAll()
    }

}