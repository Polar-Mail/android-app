package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.data.maper.toAccount
import app.polarmail.data.maper.toAccountEntity
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant

class DefaultAccountRepository(
    private val accountLocalDataSource: AccountLocalDataSource
) : AccountRepository {

    override fun observeAccounts(): Flow<List<Account>> =
        accountLocalDataSource.observeAccounts().map { it.map { it.toAccount() } }

    override fun observeSelectedAccount(): Flow<Account> =
        accountLocalDataSource.observeSelectedAccount().map { it.toAccount() }

    override suspend fun add(
        username: String,
        password: String,
        host: String,
        port: Int,
        picture: String
    ) {
        accountLocalDataSource.add(
            AccountEntity(
                0L,
                Instant.now(),
                username,
                password,
                host,
                port,
                picture,
                true // Automatically selects the new account when gets added
            )
        )
    }

    override suspend fun getAll(): List<Account> {
        return accountLocalDataSource.getAll().map { it.toAccount() }
    }

    override suspend fun getById(accountId: AccountId): Account {
        return accountLocalDataSource.getById(accountId).toAccount()
    }

    override suspend fun remove(accountId: AccountId) {
        accountLocalDataSource.remove(accountId)
    }

    override suspend fun update(account: Account) {
        accountLocalDataSource.update(account.toAccountEntity())
    }

}