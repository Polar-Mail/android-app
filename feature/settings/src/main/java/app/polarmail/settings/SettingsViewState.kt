package app.polarmail.settings

import app.polarmail.domain.model.AuthState
import io.uniflow.core.flow.data.UIState

data class SettingsViewState(val state: AuthState = AuthState.LOGGED_IN) : UIState()