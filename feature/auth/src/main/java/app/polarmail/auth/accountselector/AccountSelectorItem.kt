package app.polarmail.auth.accountselector

sealed class AccountSelectorItem {

    data class Account(
        val id: Long,
        val name: String,
        val avatar: String
    ) : AccountSelectorItem()

    object AddAccount : AccountSelectorItem()

    object Settings : AccountSelectorItem()

}