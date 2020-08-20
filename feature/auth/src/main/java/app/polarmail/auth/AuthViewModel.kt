package app.polarmail.auth

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.manager.AddAccountResult
import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.model.AuthState
import app.polarmail.domain.repository.EmailProviderRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import io.uniflow.core.flow.actionOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState as OAuthState
import timber.log.Timber

class AuthViewModel @ViewModelInject constructor(
    private val dispatcher: DispatcherProvider,
    private val emailProviderRepository: EmailProviderRepository,
    private val oAuthClient: OAuthClient,
    private val accountManager: AccountManager
) : ReduxViewModel(dispatcher) {

    private var currentProvider: AppEmailProvider? = null

    fun load(authAction: AuthAction) = action {
        val emailProviders = emailProviderRepository.getEmailProviders()
        setState(AuthViewState(emailProviders, authAction))
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

    fun handleAuthResponse(authState: OAuthState) {
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
        } finally {
            oAuthClient.signOut()
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
                val result = accountManager.addAccount(
                    account.email!!,
                    account.idToken!!,
                    currentProvider!!.emailProvider.imap.host,
                    currentProvider!!.emailProvider.imap.port,
                    account.photoUrl.toString()
                )

                when (result) {
                    is AddAccountResult.Success -> accountManager.selectAccount(result.id)
                }

                currentProvider = null
                actionOn<AuthViewState> { state ->
                    sendEvent(AuthViewEvents.LoggedIn(state.type))
                }
            }
        }

}