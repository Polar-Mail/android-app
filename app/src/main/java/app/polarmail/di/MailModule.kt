package app.polarmail.di

import app.polarmail.data.dao.FolderDao
import app.polarmail.data.email.EmailManager
import app.polarmail.data.email.LocalEmailManager
import app.polarmail.data.repository.folder.DefaultFolderRepository
import app.polarmail.data.repository.folder.FolderLocalDataSource
import app.polarmail.data.repository.folder.FolderLocalDataSourceImpl
import app.polarmail.domain.repository.AccountRepository
import app.polarmail.domain.repository.FolderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class MailModule {

    @Provides
    @Singleton
    fun provideFolderLocalDataSource(folderDao: FolderDao): FolderLocalDataSource =
        FolderLocalDataSourceImpl(folderDao)

    @Provides
    @Singleton
    fun provideFolderRepository(folderLocalDataSource: FolderLocalDataSource): FolderRepository =
        DefaultFolderRepository(folderLocalDataSource)

    @Provides
    @Singleton
    fun provideEmailManager(
        accountRepository: AccountRepository,
        folderRepository: FolderRepository
    ): EmailManager = LocalEmailManager(true, accountRepository, folderRepository)

}