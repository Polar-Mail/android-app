package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class DefaultAccountRepository(
    private val accountLocalDataSource: AccountLocalDataSource
) : AccountRepository {

    override fun observeAccounts(): Flow<List<Account>> =
        accountLocalDataSource.observeAccounts()

    override fun observeSelectedAccount(): Flow<Account> =
        accountLocalDataSource.observeSelectedAccount()

    override suspend fun add(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Account> {
        return emptyList()
    }

    override suspend fun getById(accountId: AccountId): Account {
        TODO("Not yet implemented")
    }

    override suspend fun remove(accountId: AccountId) {
        TODO("Not yet implemented")
    }

    override suspend fun update(account: Account) {
        TODO("Not yet implemented")
    }

}