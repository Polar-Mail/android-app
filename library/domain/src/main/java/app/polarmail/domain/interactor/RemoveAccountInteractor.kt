package app.polarmail.domain.interactor

import app.polarmail.core.util.AccountId
import app.polarmail.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveAccountInteractor @Inject constructor(
    private val accountRepository: AccountRepository
) : ExecuteInteractor<RemoveAccountInteractor.Params>() {

    data class Params(val id: AccountId)

    override suspend fun doWork(params: Params) {
        accountRepository.remove(params.id)
    }

}