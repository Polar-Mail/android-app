package app.polarmail.data.repository.account

import app.polarmail.core.util.AccountId
import app.polarmail.data.maper.toAccountEntity
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import dev.olog.flow.test.observer.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Instant

@ExperimentalCoroutinesApi
class DefaultAccountRepositoryTest {

    lateinit var accountRepository: AccountRepository

    private val accountLocalDataSource: AccountLocalDataSource = mockk()

    private val testScope = TestCoroutineScope()

    @Before
    fun setup() {
        accountRepository = DefaultAccountRepository(accountLocalDataSource)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `When observe account then should emit the same`() = testScope.runBlockingTest {
        // Given
        val accounts = listOf(createFakeAccount(1L))
        val channel = channelFlow {
            send(emptyList())
            send(accounts)

            awaitClose()
        }
        every { accountLocalDataSource.observeAccounts() } returns channel.map { it.map { it.toAccountEntity() } }

        // When
        val result = accountRepository.observeAccounts()

        // Then
        result.test(this) {
            assertValues(
                emptyList(),
                accounts
            )
            assertNotComplete()
        }
    }

    private fun createFakeAccount(id: Long): Account =
        Account(AccountId(id), Instant.now(), "", "", "", 0, "", true)

}