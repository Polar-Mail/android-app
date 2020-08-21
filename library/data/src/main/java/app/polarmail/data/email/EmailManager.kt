package app.polarmail.data.email

import app.polarmail.core.util.AccountId

interface EmailManager {

    suspend fun connect(accountId: AccountId)
    suspend fun disconnect(accountId: AccountId)
    suspend fun sync(accountId: AccountId)

}