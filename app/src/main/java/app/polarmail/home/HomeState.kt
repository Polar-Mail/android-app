package app.polarmail.home

import io.uniflow.core.flow.data.UIState

data class HomeState(
    val authState: HomeAuthState = HomeAuthState.LoggedOut
) : UIState()