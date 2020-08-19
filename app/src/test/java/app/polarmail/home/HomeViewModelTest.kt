package app.polarmail.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.domain.interactor.GetAccountsInteractor
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.Account
import app.polarmail.domain.model.AuthState
import app.polarmail.domain.repository.AccountRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
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


class HomeViewModelTest {

    lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    private val accountRepository: AccountRepository = mockk(relaxed = true)

    private val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    private val accountManager: AccountManager = mockk(relaxed = true)
    private val getAccountInteractor = GetAccountsInteractor(accountRepository)

    private lateinit var view: TestViewObserver

    @Test
    fun `Test logged in`() = testDispatcher.runBlockingTest {
        // Given
        val accounts = listOf(
            Account(
                AccountId(1L),
                Instant.now(),
                "whatever",
                "pass",
                "",
                9000,
                "avatar",
                true
            )
        )

        val auth = flow { emit(AuthState.LOGGED_IN) }
        every { accountManager.observeAuthState() } returns auth
        coEvery { accountRepository.getAll() } returns accounts
        viewModel = HomeViewModel(testDispatcherProvider, accountManager, getAccountInteractor)
        view = viewModel.createTestObserver()

        // When
        // nothing actually...

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(
            HomeState(
                HomeAuthState.LoggedIn(
                    accounts.first(),
                    accounts
                )
            )
        )
    }

    @Test
    fun `Test logged out`() = testDispatcher.runBlockingTest {
        // Given
        val auth = flow { emit(AuthState.LOGGED_OUT) }
        every { accountManager.observeAuthState() } returns auth
        viewModel = HomeViewModel(testDispatcherProvider, accountManager, getAccountInteractor)
        view = viewModel.createTestObserver()

        // When
        // nothing actually...

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(HomeState(HomeAuthState.LoggedOut))
    }

    @Test
    fun `Test open account selector`() = testDispatcher.runBlockingTest {
        // Given
        val auth = flow { emit(AuthState.LOGGED_OUT) }
        every { accountManager.observeAuthState() } returns auth
        viewModel = HomeViewModel(testDispatcherProvider, accountManager, getAccountInteractor)
        view = viewModel.createTestObserver()

        // When
        viewModel.openAccountSelector()

        // Then
        Truth.assertThat(view.lastEventOrNull?.peek()).isNotNull()
        Truth.assertThat(view.lastEventOrNull?.peek()).isEqualTo(HomeEvents.OpenAccountSelector)
    }

}