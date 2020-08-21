package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountLocalDataSource {

    fun observeAccounts(): Flow<List<AccountEntity>>
    fun observeAccount(id: Long): Flow<AccountEntity?>
    fun observeSelectedAccount(): Flow<AccountEntity?>

    suspend fun add(account: AccountEntity): Long
    suspend fun getAll(): List<AccountEntity>
    suspend fun getById(accountId: AccountId): AccountEntity?
    suspend fun remove(accountId: AccountId)
    suspend fun update(account: AccountEntity)

}