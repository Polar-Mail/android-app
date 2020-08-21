package app.polarmail.di

import android.content.Context
import androidx.room.Room
import app.polarmail.data.dao.AccountDao
import app.polarmail.data.dao.FolderDao
import app.polarmail.data_android.database.PolarMailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PolarMailDatabase =
        Room.databaseBuilder(context, PolarMailDatabase::class.java, "polarmail.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideAccountDao(database: PolarMailDatabase): AccountDao = database.getAccountDao()

    @Provides
    @Singleton
    fun provideFolderDao(database: PolarMailDatabase): FolderDao = database.getFolderDao()

}