package app.polarmail.domain.model

import app.polarmail.core.util.FolderId
import org.threeten.bp.Instant

data class Folder(
    val id: FolderId,
    val name: String,
    val accountId: Long,
    val folderType: FolderType,
    val unreadCount: Int,
    val lastUpdatedAt: Instant,
    val order: Int
)