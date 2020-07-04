package app.polarmail.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.AuthState
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


class HomeViewModelTest {

    lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    private val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    private val accountManager: AccountManager = mockk(relaxed = true)

    private lateinit var view: TestViewObserver

    @Test
    fun `Test logged in`() = testDispatcher.runBlockingTest {
        // Given
        val auth = flow { emit(AuthState.LOGGED_IN) }
        every { accountManager.observeAuthState() } returns auth
        viewModel = HomeViewModel(testDispatcherProvider, accountManager)
        view = viewModel.createTestObserver()

        // When
        // nothing actually...

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(HomeState(AuthState.LOGGED_IN))
    }

    @Test
    fun `Test logged out`() = testDispatcher.runBlockingTest {
        // Given
        val auth = flow { emit(AuthState.LOGGED_OUT) }
        every { accountManager.observeAuthState() } returns auth
        viewModel = HomeViewModel(testDispatcherProvider, accountManager)
        view = viewModel.createTestObserver()

        // When
        // nothing actually...

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(HomeState(AuthState.LOGGED_OUT))
    }
}