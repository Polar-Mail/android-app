package app.polarmail.domain.repository

import app.polarmail.core.util.AccountId
import app.polarmail.core.util.FolderId
import app.polarmail.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    fun observeFolders(accountId: AccountId): Flow<List<Folder>>

    suspend fun getFolders(accountId: AccountId): List<Folder>
    suspend fun addFolder(name: String, accountId: AccountId, unreadMessagesCount: Int): Long

}