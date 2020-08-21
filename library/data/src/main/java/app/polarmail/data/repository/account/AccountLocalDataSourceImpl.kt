package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.data.dao.AccountDao
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.data.maper.toAccount
import app.polarmail.domain.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AccountLocalDataSourceImpl(
    private val accountDao: AccountDao
) : AccountLocalDataSource {
    override fun observeAccounts(): Flow<List<AccountEntity>> {
        return accountDao.observeAccounts()
    }

    override fun observeAccount(id: Long): Flow<AccountEntity> {
        return accountDao.observeAccountById(id)
    }

    override fun observeSelectedAccount(): Flow<AccountEntity> {
        return accountDao.observeSelectedAccount()
    }

    override suspend fun add(account: AccountEntity): Long {
        return accountDao.insert(account)
    }

    override suspend fun getAll(): List<AccountEntity> {
        return accountDao.getAccounts()
    }

    override suspend fun getById(accountId: AccountId): AccountEntity? {
        return accountDao.getAccountById(accountId.id)
    }

    override suspend fun remove(accountId: AccountId) {
        return accountDao.deleteAccountById(accountId.id)
    }

    override suspend fun update(account: AccountEntity) {
        return accountDao.update(account)
    }
}