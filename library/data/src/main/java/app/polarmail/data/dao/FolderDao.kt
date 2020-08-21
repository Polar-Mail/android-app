package app.polarmail.data.dao

import androidx.room.Dao
import androidx.room.Query
import app.polarmail.data.entitiy.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FolderDao : EntityDao<FolderEntity>() {

    @Query("SELECT * FROM folders WHERE account_id = :accountId")
    abstract fun observeFolders(accountId: Long): Flow<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE id = :folderId")
    abstract fun observeFolder(folderId: Long) : Flow<FolderEntity>

    @Query("SELECT * FROM folders WHERE account_id = :accountId")
    abstract suspend fun getFolders(accountId: Long) : List<FolderEntity>

    @Query("SELECT * FROM folders WHERE id = :folderId")
    abstract suspend fun getFolderById(folderId: Long): FolderEntity

    @Query("DELETE FROM folders WHERE id = :folderId")
    abstract suspend fun removeFolderById(folderId: Long)

}