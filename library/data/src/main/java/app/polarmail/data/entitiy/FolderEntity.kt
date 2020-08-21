package app.polarmail.data.entitiy

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import app.polarmail.domain.model.FolderType
import org.threeten.bp.Instant

@Entity(
    tableName = "folders",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String,
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "folder_type") val folderType: FolderType,
    @ColumnInfo(name = "unread_count") val unreadCount: Int,
    @ColumnInfo(name = "last_updated_at") val lastUpdatedAt: Instant,
    val order: Int
) : EntityWithId