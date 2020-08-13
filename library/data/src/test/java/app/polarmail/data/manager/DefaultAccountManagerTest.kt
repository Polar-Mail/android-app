package app.polarmail.data.manager

import app.polarmail.core.util.AccountId
import app.polarmail.domain.interactor.AddAccountInteractor
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.Account
import app.polarmail.domain.model.AuthState
import app.polarmail.domain.repository.AccountRepository
import dev.olog.flow.test.observer.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.threeten.bp.Instant

class DefaultAccountManagerTest {

    lateinit var accountManager: AccountManager

    private val accountRepository: AccountRepository = mockk(relaxed = true)

    private val testScope = TestCoroutineScope()

    private val addAccountInteractor = AddAccountInteractor(accountRepository)

    @Test
    fun `Given accounts is not empty when observe accounts then should emit logged in`() =
        testScope.runBlockingTest {
            // Given
            val accounts = listOf(createFakeAccount(1L))
            val accountsFlow = flow {
                emit(accounts)
            }

            every { accountRepository.observeAccounts() } returns accountsFlow
            accountManager = DefaultAccountManager(accountRepository, addAccountInteractor, testScope)

            // When
            val result = accountManager.observeAuthState()

            // Then
            result.test(this) {
                assertValue(AuthState.LOGGED_IN)
            }
        }

    @Test
    fun `Given accounts is empty when observe accounts then should emit logged out`() =
        testScope.runBlockingTest {
            // Given
            val accountsFlow = flow {
                emit(emptyList<Account>())
            }

            every { accountRepository.observeAccounts() } returns accountsFlow
            accountManager = DefaultAccountManager(accountRepository, addAccountInteractor, testScope)

            // When
            val result = accountManager.observeAuthState()

            // Then
            result.test(this) {
                assertValue(AuthState.LOGGED_OUT)
            }
        }

    private fun createFakeAccount(id: Long): Account =
        Account(AccountId(id), Instant.now(), "", "", "", 0, "", true)

}