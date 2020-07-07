package app.polarmail.data_android.database

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class DbConverters {

    @TypeConverter
    fun convertStringToInstant(input: String): Instant = Instant.parse(input)

    @TypeConverter
    fun convertInstantToString(input: Instant): String = input.toString()

}