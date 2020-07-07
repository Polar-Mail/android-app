package app.polarmail.data_android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.polarmail.data.dao.AccountDao
import app.polarmail.data.entitiy.AccountEntity

@Database(entities = [AccountEntity::class], version = 2)
@TypeConverters(DbConverters::class)
abstract class PolarMailDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

}