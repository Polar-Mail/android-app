package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.domain.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountLocalDataSourceImpl : AccountLocalDataSource {
    override fun observeAccounts(): Flow<List<Account>> {
        return flow {  }
    }

    override fun observeSelectedAccount(): Flow<Account> {
        return flow {  }
    }

    override suspend fun add(account: AccountEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<AccountEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(accountId: AccountId): AccountEntity {
        TODO("Not yet implemented")
    }

    override suspend fun remove(accountId: AccountId) {
        TODO("Not yet implemented")
    }

    override suspend fun update(account: AccountEntity) {
        TODO("Not yet implemented")
    }
}