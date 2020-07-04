package app.polarmail.home

import app.polarmail.domain.model.AuthState
import io.uniflow.core.flow.data.UIState

data class HomeState(
    val authState: AuthState = AuthState.LOGGED_IN
) : UIState()