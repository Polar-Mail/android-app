package app.polarmail.data_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.polarmail.data.dao.AccountDao
import app.polarmail.data.dao.FolderDao
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.data.entitiy.FolderEntity

@Database(entities = [AccountEntity::class, FolderEntity::class], version = 3)
@TypeConverters(DbConverters::class)
abstract class PolarMailDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao
    abstract fun getFolderDao(): FolderDao

}