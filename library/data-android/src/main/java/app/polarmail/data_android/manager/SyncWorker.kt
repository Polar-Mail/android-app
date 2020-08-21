package app.polarmail.data_android.manager

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.polarmail.core.net.DispatcherProvider
import app.polarmail.core.util.AccountId
import app.polarmail.data.email.EmailManager
import app.polarmail.domain.repository.AccountRepository
import kotlinx.coroutines.withContext
import timber.log.Timber

class SyncWorker @WorkerInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dispatcher: DispatcherProvider,
    private val emailManager: EmailManager,
    private val accountRepository: AccountRepository
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val PARAM_ACCOUNT_ID = "account_id"
    }

    override suspend fun doWork(): Result = withContext(dispatcher.io) {
        val accountId = inputData.getLong(PARAM_ACCOUNT_ID, -1)

        if (accountId <= 0) {
            return@withContext Result.failure()
        }

        val account = accountRepository.getById(AccountId(accountId))

        if (account == null) {
            Timber.e("Account doesn't exist")
            return@withContext Result.failure()
        }

        // TODO check if we should sync or not depending settings

        emailManager.connect(account.id)
        emailManager.sync(account.id)
        emailManager.disconnect(account.id)

        Result.success()
    }

}