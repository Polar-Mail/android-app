package app.polarmail.data.dao

import androidx.room.Dao
import androidx.room.Query
import app.polarmail.data.entitiy.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AccountDao : EntityDao<AccountEntity>() {

    @Query("SELECT * FROM accounts")
    abstract fun observeAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    abstract fun observeAccountById(id: Long): Flow<AccountEntity>

    @Query("SELECT * FROM accounts WHERE is_selected = 1 LIMIT 1")
    abstract fun observeSelectedAccount(): Flow<AccountEntity>

    @Query("SELECT * FROM accounts")
    abstract suspend fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE id = :id")
    abstract suspend fun getAccountById(id: Long): AccountEntity

    @Query("DELETE FROM accounts WHERE id = :id")
    abstract suspend fun deleteAccountById(id: Long)

}