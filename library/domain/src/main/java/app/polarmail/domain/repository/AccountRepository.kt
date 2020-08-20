package app.polarmail.domain.repository

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun observeAccounts(): Flow<List<Account>>
    fun observeAccount(id: Long): Flow<Account?>
    fun observeSelectedAccount(): Flow<Account?>

    suspend fun add(username: String, password: String, host: String, port: Int, picture: String): Long
    suspend fun getAll(): List<Account>
    suspend fun getById(accountId: AccountId): Account
    suspend fun remove(accountId: AccountId)
    suspend fun update(account: Account)

}