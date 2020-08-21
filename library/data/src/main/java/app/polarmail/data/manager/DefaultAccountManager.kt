package app.polarmail.data.manager

import app.polarmail.core.di.qualifiers.AppScope
import app.polarmail.core.util.AccountId
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.manager.AddAccountResult
import app.polarmail.domain.manager.SyncManager
import app.polarmail.domain.model.Account
import app.polarmail.domain.model.AuthState
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DefaultAccountManager(
    private val accountRepository: AccountRepository,
    private val syncManager: SyncManager,
    @AppScope appScope: CoroutineScope
) : AccountManager {

    private val _authState: ConflatedBroadcastChannel<AuthState> =
        ConflatedBroadcastChannel()
    private val authState: Flow<AuthState> = _authState.asFlow()

    private val _accountSelected = ConflatedBroadcastChannel<Account>()
    private val accountSelected: Flow<Account> = _accountSelected.asFlow()

    private val _accounts = ConflatedBroadcastChannel<List<Account>>(emptyList())
    private val accounts: Flow<List<Account>> = _accounts.asFlow()

    init {
        appScope.launch {
            accountRepository.observeAccounts().collect {
                try {
                    _accounts.send(it)

                    val authState = if (it.isNotEmpty()) {
                        AuthState.LOGGED_IN
                    } else {
                        AuthState.LOGGED_OUT
                    }
                    _authState.send(authState)

                    it.forEach {
                        syncManager.scheduleSync(it.id)
                    }

                } catch (exception: Exception) {
                    // Channel has been closed
                }
            }
            accountRepository.observeSelectedAccount().collect {
                it?.let {
                    _accountSelected.send(it)
                }
            }
        }
    }

    override fun observeAuthState(): Flow<AuthState> = authState

    override fun observeAccountSelected(): Flow<Account> = accountSelected

    override fun observeAccounts(): Flow<List<Account>> = accounts

    override suspend fun addAccount(
        username: String,
        password: String,
        host: String,
        port: Int,
        picture: String
    ): AddAccountResult {
        return try {
            val id = accountRepository.add(
                username,
                password,
                host,
                port,
                picture
            )
            AddAccountResult.Success(AccountId(id))
        } catch (e: Exception) {
            AddAccountResult.Failure
        }
    }

    override suspend fun removeAccount(accountId: AccountId) {
        accountRepository.remove(accountId)
    }

    override suspend fun selectAccount(accountId: AccountId) {
        val accounts = accountRepository.getAll()

        // Set all accounts to not selected expect the wanted one
        accounts
            .map { it.copy(isSelected = false) }
            .forEach {
                accountRepository.update(it)
            }

        accounts.filter { it.id == accountId }
            .map { it.copy(isSelected = true) }
            .forEach {
                accountRepository.update(it)
            }
    }

}