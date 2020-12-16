package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.udacity.asteroidradar.database.asteroid.AsteroidEntity
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate


@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {

    fun toEntity() = AsteroidEntity(
        id, codename, LocalDate.parse(closeApproachDate), absoluteMagnitude,
        estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
    )
}

fun List<Asteroid>.asEntity() = map { it.toEntity() }