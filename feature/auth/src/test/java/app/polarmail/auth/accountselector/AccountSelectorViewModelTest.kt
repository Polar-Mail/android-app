package app.polarmail.auth.accountselector

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.polarmail.auth.AuthViewModel
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.domain.interactor.ObserveAccountsInteractor
import app.polarmail.domain.model.Account
import app.polarmail.domain.repository.AccountRepository
import app.polarmail.domain.repository.EmailProviderRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import io.uniflow.test.rule.TestDispatchersRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.Instant

class AccountSelectorViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    private val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    lateinit var view: TestViewObserver

    private val repo: AccountRepository = mockk(relaxed = true)

    private lateinit var viewModel: AccountSelectorViewModel

    val observeAccountInteractor = ObserveAccountsInteractor(repo)

    @Test
    fun `test load`() = testDispatcher.runBlockingTest {
        // Given
        val accounts = listOf(
            Account(
                AccountId(1L),
                Instant.now(),
                "costular@gmail.com",
                "okok",
                "costular.com",
                8080,
                "avatar",
                true
            ),
            Account(
                AccountId(2L),
                Instant.now(),
                "costular2@gmail.com",
                "okok2",
                "costular2.com",
                8080,
                "avatar2",
                true
            )
        )
        every { repo.observeAccounts() } returns flow { emit(accounts) }
        viewModel = AccountSelectorViewModel(testDispatcherProvider, observeAccountInteractor)
        view = viewModel.createTestObserver()

        // When


        // Then
        val expected = mutableListOf<AccountSelectorItem>()
        expected.run {
            add(AccountSelectorItem.AddAccount)
            addAll(accounts.map { AccountSelectorItem.Account(it.id.id, it.username, it.avatar, it.isSelected) })
            add(AccountSelectorItem.Settings)
        }
        Truth.assertThat((view.lastStateOrNull as AccountSelectorViewState).items)
            .isEqualTo(expected)
    }

}