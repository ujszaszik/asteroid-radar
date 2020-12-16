package com.udacity.asteroidradar.database

import androidx.room.TypeConverter
import com.udacity.asteroidradar.model.dateFormatter
import org.threeten.bp.LocalDate

class LocalDateTypeConverter {

    companion object {

        @JvmStatic
        @TypeConverter
        fun toLocalDate(dateText: String?): LocalDate? = LocalDate.parse(dateText, dateFormatter)

        @JvmStatic
        @TypeConverter
        fun fromLocalDate(date: LocalDate?): String? = dateFormatter.format(date)

    }
}