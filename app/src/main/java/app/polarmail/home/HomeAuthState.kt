package app.polarmail.home

import app.polarmail.domain.model.Account
import app.polarmail.domain.model.Folder


sealed class HomeAuthState {

    data class LoggedIn(
        val accountSelected: Account,
        val accounts: List<Account>,
        val folders: List<Folder> = emptyList()
    ) : HomeAuthState()

    object LoggedOut : HomeAuthState()

}