package app.polarmail.domain.manager

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import app.polarmail.domain.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AccountManager {

    fun observeAuthState(): Flow<AuthState>
    fun observeAccountSelected(): Flow<Account>
    fun observeAccounts(): Flow<List<Account>>

    suspend fun addAccount(
        username: String,
        password: String,
        host: String,
        port: Int,
        picture: String
    )

    suspend fun removeAccount(accountId: AccountId)
    suspend fun selectAccount(accountId: AccountId)

}