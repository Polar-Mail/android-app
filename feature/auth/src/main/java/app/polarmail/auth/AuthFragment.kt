package app.polarmail.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.polarmail.auth.databinding.FragmentAuthBinding
import app.polarmail.core_ui.util.NavigationLink
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.flow.onEvents
import io.uniflow.android.flow.onStates
import net.openid.appauth.*

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by viewModels()

    lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        initRecycler()
        listenState()

        val isFresh = requireNotNull(arguments).getBoolean(AuthActivity.ARG_IS_FRESH)
        viewModel.load(if (isFresh) AuthAction.FRESH else AuthAction.ANOTHER)
    }

    private fun initRecycler() {
        binding.epoxyProviders.layoutManager =
            GridLayoutManager(
                requireActivity(),
                resources.getInteger(R.integer.grid_span),
                RecyclerView.VERTICAL,
                false
            )
    }

    private fun listenState() {
        onStates(viewModel) { state ->
            when (state) {
                is AuthViewState -> handleState(state)
            }
        }
        onEvents(viewModel) { event ->
            when (val data = event.take()) {
                is AuthViewEvents -> handleEvents(data)
            }
        }
    }

    private fun handleState(state: AuthViewState) {
        binding.epoxyProviders.withModels {
            state.providers.forEach { provider ->
                emailProvider {
                    id(provider.emailProvider.id)
                    icon(provider.icon)
                    name(provider.name)
                    clickListener {
                        viewModel.authorize(provider)
                    }
                }
            }
        }
    }

    private fun handleEvents(event: AuthViewEvents) {
        listOf(
            when (event) {
                is AuthViewEvents.OAuthRequest -> sendOAuthRequest(event.request)
                is AuthViewEvents.GoogleSignIn -> sendGoogleSignIntent(event.intent)
                is AuthViewEvents.LoggedIn -> navigateTo(event.authAction)
            }
        )
    }

    private fun sendGoogleSignIntent(intent: Intent) {
        startActivityForResult(intent, RC_GOOGLE_SIGN_IN)
    }

    private fun sendOAuthRequest(request: AuthorizationRequest) {
        val authService = AuthorizationService(requireActivity())
        val authIntent = authService.getAuthorizationRequestIntent(request)
        startActivityForResult(authIntent, RC_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            requestCode == RC_AUTH && data != null -> {
                val authResponse = AuthorizationResponse.fromIntent(data)
                val authException = AuthorizationException.fromIntent(data)
                val authState = AuthState(authResponse, authException)
                viewModel.handleAuthResponse(authState)
            }
            requestCode == RC_GOOGLE_SIGN_IN && data != null -> {
                viewModel.handleGoogleResult(data)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun navigateTo(authAction: AuthAction) {
        if (authAction == AuthAction.FRESH) {
            val intent = Intent(NavigationLink.HOME).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        } else {
            (requireActivity() as? AuthActivity)?.finish()
        }
    }

    companion object {
        private const val RC_AUTH = 200
        private const val RC_GOOGLE_SIGN_IN = 300
    }

}