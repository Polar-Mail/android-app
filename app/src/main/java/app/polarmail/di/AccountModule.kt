package app.polarmail.di

import app.polarmail.core.di.qualifiers.AppScope
import app.polarmail.data.manager.DefaultAccountManager
import app.polarmail.data.repository.account.AccountLocalDataSource
import app.polarmail.data.repository.account.AccountLocalDataSourceImpl
import app.polarmail.data.repository.account.DefaultAccountRepository
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.repository.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AccountModule {

    @Provides
    @Singleton
    fun provideAccountLocalDataSource(): AccountLocalDataSource = AccountLocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideAccountRepository(accountLocalDataSource: AccountLocalDataSource): AccountRepository =
        DefaultAccountRepository(accountLocalDataSource)

    @Provides
    @Singleton
    fun provideAccountManager(
        accountRepository: AccountRepository,
        @AppScope appScope: CoroutineScope
    ): AccountManager =
        DefaultAccountManager(accountRepository, appScope)

}