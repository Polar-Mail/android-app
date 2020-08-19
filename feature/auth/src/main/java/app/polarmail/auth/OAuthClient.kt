package app.polarmail.auth

import android.content.Context
import androidx.core.net.toUri
import app.polarmail.domain.model.OAuth2Info
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

interface OAuthClient {

    suspend fun buildGoogleSignIn(oAuth2Info: OAuth2Info): GoogleSignInClient
    suspend fun buildRequest(oAuth2Info: OAuth2Info): AuthorizationRequest
    suspend fun signOut()

}

class DefaultOAuthClient(
    private val context: Context
) : OAuthClient {

    private val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken("623417360377-bk18g52llk9h8ebbojcv1i9t0ngu72nb.apps.googleusercontent.com")
        .requestScopes(Scope(Scopes.EMAIL))
        //.requestScopes(Scope(oAuth2Info.scopes.first { it.contains("mail") }))
        .build()

    override suspend fun buildGoogleSignIn(oAuth2Info: OAuth2Info): GoogleSignInClient {
        return GoogleSignIn.getClient(context, options)
    }

    override suspend fun buildRequest(oAuth2Info: OAuth2Info): AuthorizationRequest {
        val serviceConfig = AuthorizationServiceConfiguration(
            oAuth2Info.authorizationUrl.toUri(),
            oAuth2Info.tokenEndpoint.toUri()
        )

        val redirect = oAuth2Info.redirectUri.toUri()

        val request = AuthorizationRequest.Builder(
            serviceConfig,
            oAuth2Info.clientId,
            ResponseTypeValues.CODE,
            redirect
        )
        request.setScope(oAuth2Info.scopes.joinToString(separator = " "))
        return request.build()
    }

    override suspend fun signOut() {
        GoogleSignIn.getClient(context, options).signOut()
    }

}