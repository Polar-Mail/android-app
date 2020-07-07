package app.polarmail.home

import app.polarmail.domain.model.Account


sealed class HomeAuthState {

    data class LoggedIn(
        val accountSelected: Account,
        val accounts: List<Account>
    ) : HomeAuthState()

    object LoggedOut : HomeAuthState()

}