package app.polarmail.auth

import android.view.ViewTreeObserver
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.model.EmailProvider
import app.polarmail.domain.model.HostInfo
import app.polarmail.domain.model.OAuth2Info
import app.polarmail.domain.repository.EmailProviderRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import io.uniflow.core.flow.data.UIState
import io.uniflow.test.rule.TestDispatchersRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test


class AuthViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    val testDispatchersRule = TestDispatchersRule()

    private val testDispatcher = testDispatchersRule.testCoroutineDispatcher
    private val testDispatcherProvider = TestDispatcherProvider(testDispatcher)

    lateinit var view: TestViewObserver

    private val repo: EmailProviderRepository = mockk(relaxed = true)

    private lateinit var viewModel: AuthViewModel

    @Test
    fun `Test load`() = testDispatcher.runBlockingTest {
        // Given
        val providers = listOf(createMailProvider())
        every { repo.getEmailProviders() } returns providers
        viewModel = AuthViewModel(testDispatcherProvider, repo)
        view = viewModel.createTestObserver()

        // When
        // nothing

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(AuthViewState(providers))
    }

    private fun createMailProvider(): AppEmailProvider = object : AppEmailProvider {
        override val emailProvider: EmailProvider = object : EmailProvider {
            override val id: String = "whatever"
            override val name: String = "name"
            override val imap: HostInfo = HostInfo("imap.host", 8080)
            override val smtp: HostInfo = HostInfo("smtp.host", 8080)
            override val oauth2: OAuth2Info? = null

        }
        override val icon: Int = 1
        override val name: String = "Whatever"

    }

}