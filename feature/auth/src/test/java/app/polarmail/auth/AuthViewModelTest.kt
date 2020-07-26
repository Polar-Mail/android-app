package app.polarmail.auth

import android.view.ViewTreeObserver
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.net.toUri
import app.polarmail.core.net.TestDispatcherProvider
import app.polarmail.domain.manager.AccountManager
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
import io.uniflow.test.rule.TestDispatchersRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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

    private val oauthClient: OAuthClient = mockk(relaxed = true)
    private val accountManager: AccountManager = mockk(relaxed = true)

    @Test
    fun `Test load`() = testDispatcher.runBlockingTest {
        // Given
        val providers = listOf(createMailProvider())
        every { repo.getEmailProviders() } returns providers
        viewModel = AuthViewModel(testDispatcherProvider, repo, oauthClient, accountManager)
        view = viewModel.createTestObserver()

        // When
        // nothing

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(AuthViewState(providers))
    }

    /*
    @Test
    fun `Test auth`() = testDispatcher.runBlockingTest {
        // Given
        val providers = listOf(createMailProvider())
        every { repo.getEmailProviders() } returns providers
        viewModel = AuthViewModel(testDispatcherProvider, repo, DefaultOAuthClient())
        view = viewModel.createTestObserver()

        // When
        viewModel.authorize(providers.first())


        // Then
        val event = (view.lastEventOrNull as AuthViewEvents.OAuthRequest)
        Truth.assertThat(event.request.clientId)
            .isEqualTo(providers.first().emailProvider.oauth2!!.clientId)
        Truth.assertThat(event.request.scope).isEqualTo("scope scope2")
    }*/

    private fun createMailProvider(): AppEmailProvider = object : AppEmailProvider {
        override val emailProvider: EmailProvider = object : EmailProvider {
            override val id: String = "whatever"
            override val name: String = "name"
            override val imap: HostInfo = HostInfo("imap.host", 8080)
            override val smtp: HostInfo = HostInfo("smtp.host", 8080)
            override val oauth2: OAuth2Info? = OAuth2Info(
                "auth-url",
                "client-id",
                "client-secret",
                "redirect-url",
                listOf("scope", "scope2"),
                "token-endpoint"
            )

        }
        override val icon: Int = 1
        override val name: String = "Whatever"

    }

}