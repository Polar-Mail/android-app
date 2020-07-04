package app.polarmail.auth

import app.polarmail.core.net.DispatcherProvider
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    dispatcher: DispatcherProvider
) : CoroutineViewModel(dispatcher) {



}