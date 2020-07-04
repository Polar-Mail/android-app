package app.polarmail.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.polarmail.data.email.FolderType

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)val id: Long,
    val name: String,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "folder_type") val folderType: FolderType
)