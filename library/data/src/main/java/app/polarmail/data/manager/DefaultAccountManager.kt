package app.polarmail.data.manager

import app.polarmail.core.di.qualifiers.AppScope
import app.polarmail.core.util.AccountId
import app.polarmail.domain.manager.AccountManager
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
    @AppScope appScope: CoroutineScope
) : AccountManager {

    private val _authState: ConflatedBroadcastChannel<AuthState> =
        ConflatedBroadcastChannel(AuthState.LOGGED_OUT)
    private val authState: Flow<AuthState> = _authState.asFlow()

    private val _accountSelected = ConflatedBroadcastChannel<Account>()
    private val accountSelected: Flow<Account> = _accountSelected.asFlow()

    private val _accounts = ConflatedBroadcastChannel<List<Account>>(emptyList())
    private val accounts: Flow<List<Account>> = _accounts.asFlow()

    init {
        appScope.launch {
            accountRepository.observeAccounts().collect {
                _accounts.send(it)

                val authState = if (it.isNotEmpty()) {
                    AuthState.LOGGED_IN
                } else {
                    AuthState.LOGGED_OUT
                }
                _authState.send(authState)
            }
            accountRepository.observeSelectedAccount().collect {
                _accountSelected.send(it)
            }
        }
    }

    override fun observeAuthState(): Flow<AuthState> = authState

    override fun observeAccountSelected(): Flow<Account> = accountSelected

    override fun observeAccounts(): Flow<List<Account>> = accounts

    override suspend fun addAccount(account: Account) {
        accountRepository.add(account)
    }

    override suspend fun removeAccount(accountId: AccountId) {
        accountRepository.remove(accountId)
    }

    override suspend fun selectAccount(accountId: AccountId) {
        // TODO
    }

}