package app.polarmail.data.repository.folder

import app.polarmail.core.util.AccountId
import app.polarmail.core.util.FolderId
import app.polarmail.data.entitiy.FolderEntity
import app.polarmail.data.maper.toFolder
import app.polarmail.domain.model.Folder
import app.polarmail.domain.model.FolderType
import app.polarmail.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant

class DefaultFolderRepository(
    private val folderLocalDataSource: FolderLocalDataSource
) : FolderRepository {

    override fun observeFolders(accountId: AccountId): Flow<List<Folder>> {
        return folderLocalDataSource.observeFolders(accountId.id).map { it.map { it.toFolder() } }
    }

    override suspend fun getFolders(accountId: AccountId): List<Folder> {
        return folderLocalDataSource.getFolders(accountId.id).map { it.toFolder() }
    }

    override suspend fun addFolder(
        name: String,
        accountId: AccountId,
        unreadMessagesCount: Int
    ): Long {
        val folderType = when {
            name.contains("inbox", true) -> FolderType.INBOX
            else -> FolderType.REGULAR
        }
        val folder = FolderEntity(
            0,
            name,
            accountId.id,
            folderType,
            unreadMessagesCount,
            Instant.now(),
            1
        )

        return folderLocalDataSource.add(folder)
    }

}