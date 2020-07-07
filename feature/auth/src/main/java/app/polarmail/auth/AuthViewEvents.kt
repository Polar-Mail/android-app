package app.polarmail.auth

import android.content.Intent
import io.uniflow.core.flow.data.UIEvent
import net.openid.appauth.AuthorizationRequest

sealed class AuthViewEvents : UIEvent() {

    data class OAuthRequest(val request: AuthorizationRequest) : AuthViewEvents()

    data class GoogleSignIn(val intent: Intent) : AuthViewEvents()

}

