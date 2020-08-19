package app.polarmail.settings.accounts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.domain.interactor.ObserveAccountsInteractor
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import io.uniflow.test.rule.TestDispatchersRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant

class AccountSettingsViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    private val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    lateinit var view: TestViewObserver
    lateinit var viewModel: AccountSettingsViewModel

    private val accountRepository: AccountRepository = mockk(relaxed = true)
    private val observeAccounts = ObserveAccountsInteractor(accountRepository)

    @Test
    fun `Test load`() = testDispatcher.runBlockingTest {
        // Given
        val accounts = factoryAccounts()
        every { accountRepository.observeAccounts() } returns flow { emit(accounts) }
        viewModel = AccountSettingsViewModel(testDispatcherProvider, observeAccounts)
        view = viewModel.createTestObserver()

        // When
        // called automatically

        // Then
        val expected = listOf(
            AccountSettingsItem.AccountItem(accounts.first()),
            AccountSettingsItem.AddAccount
        )
        Truth.assertThat(view.lastStateOrNull).isEqualTo(AccountSettingsState(expected))
    }

    private fun factoryAccounts() = listOf(
        Account(
            AccountId(1L),
            Instant.now(),
            "username",
            "pass",
            "host",
            90,
            "",
            true
        )
    )

}