package app.polarmail.domain.model

data class AccountState(
    val accounts: List<Account>,
    val selectedAccount: Account,
    val authState: AuthState
)