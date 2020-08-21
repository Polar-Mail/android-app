package app.polarmail.data_android.database

import androidx.room.TypeConverter
import app.polarmail.domain.model.FolderType
import org.threeten.bp.Instant

class DbConverters {

    @TypeConverter
    fun convertStringToInstant(input: String): Instant = Instant.parse(input)

    @TypeConverter
    fun convertInstantToString(input: Instant): String = input.toString()

    @TypeConverter
    fun convertToStringToFolderType(input: String): FolderType = FolderType.valueOf(input)

    @TypeConverter
    fun convertFolderTypeToString(input: FolderType): String = input.name

}