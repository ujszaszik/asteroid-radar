package com.udacity.asteroidradar.database.asteroid

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.asFormattedString
import org.threeten.bp.LocalDate

@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codename: String,
    val closeApproachDate: LocalDate,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) {

    fun toModel() = Asteroid(
        id, codename, closeApproachDate.asFormattedString(), absoluteMagnitude,
        estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
    )

}

fun List<AsteroidEntity>.asModel() = map { it.toModel() }