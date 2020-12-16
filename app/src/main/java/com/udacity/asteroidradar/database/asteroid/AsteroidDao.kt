package com.udacity.asteroidradar.database.asteroid

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDate

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids WHERE closeApproachDate >= date('now')  ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT closeApproachDate FROM asteroids ORDER BY closeApproachDate DESC LIMIT 1")
    suspend fun getLastDayFetched(): LocalDate?

    @Query("DELETE FROM asteroids WHERE closeApproachDate < :exclusiveDate")
    suspend fun deleteBefore(exclusiveDate: LocalDate)
}