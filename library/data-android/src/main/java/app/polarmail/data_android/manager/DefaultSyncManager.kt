package app.polarmail.data_android.manager

import android.content.Context
import androidx.work.*
import app.polarmail.core.util.AccountId
import app.polarmail.domain.manager.SyncManager

class DefaultSyncManager(val context: Context) : SyncManager {

    override suspend fun scheduleSync(accountId: AccountId) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag(TAG)
            .setConstraints(constraints)
            .setInputData(workDataOf(SyncWorker.PARAM_ACCOUNT_ID to accountId.id))
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(accountId.id.toString(), ExistingWorkPolicy.KEEP, workRequest)
    }

    override suspend fun cancelScheduled(accountId: AccountId) {
        WorkManager.getInstance(context).cancelUniqueWork(accountId.id.toString())
    }

    override suspend fun cancelAllScheduled() {
        WorkManager.getInstance(context).cancelAllWorkByTag(TAG)
    }

    companion object {
        private const val TAG = "sync"
    }

}