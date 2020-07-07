package app.polarmail.di

import android.content.Context
import app.polarmail.auth.DefaultOAuthClient
import app.polarmail.auth.OAuthClient
import app.polarmail.core.di.qualifiers.AppScope
import app.polarmail.data.dao.AccountDao
import app.polarmail.data.manager.DefaultAccountManager
import app.polarmail.data.repository.account.AccountLocalDataSource
import app.polarmail.data.repository.account.AccountLocalDataSourceImpl
import app.polarmail.data.repository.account.DefaultAccountRepository
import app.polarmail.data_android.emailproviders.EmailProviderRepositoryImpl
import app.polarmail.domain.interactor.AddAccountInteractor
import app.polarmail.domain.manager.AccountManager
import app.polarmail.domain.model.EmailProvider
import app.polarmail.domain.repository.AccountRepository
import app.polarmail.domain.repository.EmailProviderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AccountModule {

    @Provides
    @Singleton
    fun provideAccountLocalDataSource(accountDao: AccountDao): AccountLocalDataSource =
        AccountLocalDataSourceImpl(accountDao)

    @Provides
    @Singleton
    fun provideAccountRepository(accountLocalDataSource: AccountLocalDataSource): AccountRepository =
        DefaultAccountRepository(accountLocalDataSource)

    @Provides
    @Singleton
    fun provideAccountManager(
        accountRepository: AccountRepository,
        addAccountInteractor: AddAccountInteractor,
        @AppScope appScope: CoroutineScope
    ): AccountManager =
        DefaultAccountManager(accountRepository, addAccountInteractor, appScope)

    @Provides
    @Singleton
    fun provideEmailProviderRepository(): EmailProviderRepository = EmailProviderRepositoryImpl()

    @Provides
    @Singleton
    fun provideOAuthclient(@ApplicationContext context: Context): OAuthClient =
        DefaultOAuthClient(context)

}