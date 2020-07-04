package app.polarmail.data.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "last_connected") val lastConnected: Instant,
    val username: String,
    val password: String,
    val host: String,
    val port: Int
)