package app.polarmail.domain.interactor

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddAccountInteractor @Inject constructor(
    private val accountRepository: AccountRepository
) : ExecuteInteractor<AddAccountInteractor.Params>() {

    data class Params(
        val username: String,
        val password: String,
        val host: String,
        val port: Int,
        val picture: String
    )

    override suspend fun doWork(params: Params) {
        accountRepository.add(
            params.username,
            params.password,
            params.host,
            params.port,
            params.picture
        )
    }
}