package com.udacity.asteroidradar.database.picture

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.asFormattedString
import org.threeten.bp.LocalDate

@Entity(tableName = "daily_pictures")
data class PictureOfDayEntity(
    @PrimaryKey
    var dateTaken: LocalDate,
    var mediaType: String,
    var title: String,
    var url: String
) {

    fun toModel() = PictureOfDay(dateTaken.asFormattedString(), mediaType, title, url)
}