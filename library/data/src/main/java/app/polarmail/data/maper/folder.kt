package app.polarmail.data.maper

import app.polarmail.core.util.FolderId
import app.polarmail.data.entitiy.FolderEntity
import app.polarmail.domain.model.Folder

fun FolderEntity.toFolder(): Folder =
    Folder(
        FolderId(this.id),
        this.name,
        this.accountId,
        this.folderType,
        this.unreadCount,
        this.lastUpdatedAt,
        this.order
    )

fun Folder.toFolderEntity(): FolderEntity = FolderEntity(
    this.id.id,
    this.name,
    this.accountId,
    this.folderType,
    this.unreadCount,
    this.lastUpdatedAt,
    this.order
)