package app.polarmail.data.repository.folder

import app.polarmail.data.entitiy.FolderEntity
import kotlinx.coroutines.flow.Flow

interface FolderLocalDataSource {

    fun observeFolders(account: Long): Flow<List<FolderEntity>>
    fun observeFolder(folderId: Long): Flow<FolderEntity>

    suspend fun getFolders(account: Long): List<FolderEntity>
    suspend fun getFolderById(id: Long): FolderEntity
    suspend fun add(folder: FolderEntity): Long
    suspend fun removeById(id: Long)
    suspend fun update(folder: FolderEntity)

}