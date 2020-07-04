package app.polarmail.core_ui.mvi

import app.polarmail.core.net.DispatcherProvider
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

abstract class ReduxViewModel(
    private val dispatcher: DispatcherProvider,
    initialState: UIState = UIState.Empty
) : AndroidDataFlow(initialState, defaultDispatcher = dispatcher.computation) {



}