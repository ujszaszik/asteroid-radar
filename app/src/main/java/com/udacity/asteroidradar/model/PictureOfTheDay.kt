package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.picture.PictureOfDayEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureOfDay(
    val date: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
) : Parcelable {

    fun toEntity() =
        PictureOfDayEntity(date.asLocalDate(), mediaType, title, url)
}