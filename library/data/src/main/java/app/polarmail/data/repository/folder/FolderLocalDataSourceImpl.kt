package app.polarmail.data.repository.folder

import app.polarmail.data.dao.FolderDao
import app.polarmail.data.entitiy.FolderEntity
import kotlinx.coroutines.flow.Flow

class FolderLocalDataSourceImpl(
    private val folderDao: FolderDao
) : FolderLocalDataSource {

    override fun observeFolders(account: Long): Flow<List<FolderEntity>> {
        return folderDao.observeFolders(account)
    }

    override fun observeFolder(folderId: Long): Flow<FolderEntity> {
        return folderDao.observeFolder(folderId)
    }

    override suspend fun getFolders(accountId: Long): List<FolderEntity> {
        return folderDao.getFolders(accountId)
    }

    override suspend fun getFolderById(id: Long): FolderEntity {
        return folderDao.getFolderById(id)
    }

    override suspend fun add(folder: FolderEntity): Long {
        return folderDao.insertOrUpdate(folder)
    }

    override suspend fun removeById(id: Long) {
        folderDao.removeFolderById(id)
    }

    override suspend fun update(folder: FolderEntity) {
        folderDao.update(folder)
    }

}