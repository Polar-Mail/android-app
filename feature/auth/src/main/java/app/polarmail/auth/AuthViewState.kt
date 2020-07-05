package app.polarmail.auth

import app.polarmail.domain.model.AppEmailProvider
import io.uniflow.core.flow.data.UIState

data class AuthViewState(
    val providers: List<AppEmailProvider> = emptyList()
) : UIState()