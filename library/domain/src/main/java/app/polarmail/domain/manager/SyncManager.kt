package app.polarmail.domain.manager

import app.polarmail.core.util.AccountId

interface SyncManager {

    suspend fun scheduleSync(accountId: AccountId)
    suspend fun cancelScheduled(accountId: AccountId)
    suspend fun cancelAllScheduled()

}