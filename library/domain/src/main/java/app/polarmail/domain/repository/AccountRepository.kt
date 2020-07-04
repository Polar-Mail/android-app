package app.polarmail.domain.repository

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun observeAccounts(): Flow<List<Account>>
    fun observeSelectedAccount(): Flow<Account>

    suspend fun add(account: Account)
    suspend fun getAll(): List<Account>
    suspend fun getById(accountId: AccountId): Account
    suspend fun remove(accountId: AccountId)
    suspend fun update(account: Account)

}