package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.database.asteroid.AsteroidDao
import com.udacity.asteroidradar.database.asteroid.AsteroidEntity
import com.udacity.asteroidradar.database.picture.PictureDao
import com.udacity.asteroidradar.database.picture.PictureOfDayEntity

@Database(
    entities = [AsteroidEntity::class, PictureOfDayEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(LocalDateTypeConverter::class)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidDao

    abstract fun pictureDao(): PictureDao

}