package app.polarmail.auth

import android.content.Intent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.repository.EmailProviderRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState
import timber.log.Timber
import java.lang.IllegalStateException


class AuthViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val emailProviderRepository: EmailProviderRepository,
    private val oAuthClient: OAuthClient,
    private val accountManager: AccountManager
) : ReduxViewModel(dispatcher) {

    private var currentProvider: AppEmailProvider? = null

    init {
        load()
    }

    fun load() = action {
        val emailProviders = emailProviderRepository.getEmailProviders()
        setState(AuthViewState(emailProviders))
    }

    fun authorize(provider: AppEmailProvider) = viewModelScope.launch(dispatcher.main) {
        currentProvider = provider
        if (provider.emailProvider.id == "gmail") {
            val googleSignIn = withContext(dispatcher.computation) {
                oAuthClient.buildGoogleSignIn(provider.emailProvider.oauth2!!)
            }
            action {
                sendEvent(AuthViewEvents.GoogleSignIn(googleSignIn.signInIntent))
            }
        } else {
            if (provider.emailProvider.oauth2 != null) {
                val request = withContext(dispatcher.computation) {
                    oAuthClient.buildRequest(provider.emailProvider.oauth2!!)
                }
                action {
                    sendEvent(AuthViewEvents.OAuthRequest(request))
                }
            }
        }
    }

    fun handleAuthResponse(authState: AuthState) {
        if (authState.isAuthorized && authState.accessToken != null) {
            val token = authState.accessToken

            token?.let {
                setAccountWithToken(token)
            } ?: showErrorMessage()

        } else {
            Timber.d("AUTH - Authorization failed!")
            Timber.e(authState.authorizationException)
        }
    }

    fun handleGoogleResult(data: Intent) = viewModelScope.launch(dispatcher.main) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.let {
                setAccountWithGoogle(it)
            } ?: showErrorMessage()

        } catch (e: ApiException) {
            Timber.e(e)
            // TODO
        }
    }

    private fun showErrorMessage() {
        // TODO show snackbar or something
    }

    private fun setAccountWithToken(token: String) {
        // TODO
    }

    private fun setAccountWithGoogle(account: GoogleSignInAccount) =
        viewModelScope.launch(dispatcher.main) {
            requireNotNull(currentProvider)

            withContext(dispatcher.io) {
                accountManager.addAccount(
                    account.email!!,
                    account.idToken!!,
                    currentProvider!!.emailProvider.imap.host,
                    currentProvider!!.emailProvider.imap.port,
                    account.photoUrl.toString()
                )
            }

            currentProvider = null
        }

}