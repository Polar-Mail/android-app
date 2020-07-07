package app.polarmail.domain.model

import app.polarmail.core.util.AccountId
import org.threeten.bp.Instant

data class Account(
    val id: AccountId,
    val lastConnected: Instant,
    val username: String,
    val password: String,
    val host: String,
    val port: Int,
    val avatar: String,
    val isSelected: Boolean
)