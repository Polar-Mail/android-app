package app.polarmail.auth

import androidx.hilt.lifecycle.ViewModelInject
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core_ui.mvi.ReduxViewModel
import app.polarmail.domain.model.AppEmailProvider
import app.polarmail.domain.repository.EmailProviderRepository

class AuthViewModel @ViewModelInject constructor(
    dispatcher: DispatcherProvider,
    private val emailProviderRepository: EmailProviderRepository
) : ReduxViewModel(dispatcher) {

    init {
        load()
    }

    fun load() = action {
        val emailProviders = emailProviderRepository.getEmailProviders()
        setState(AuthViewState(emailProviders))
    }

}