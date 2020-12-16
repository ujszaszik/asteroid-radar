package com.udacity.asteroidradar.database.picture

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.threeten.bp.LocalDate

@Dao
interface PictureDao {

    @Query("SELECT * FROM daily_pictures ORDER BY dateTaken DESC LIMIT 1")
    fun getPictureOfThisDay(): LiveData<PictureOfDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(picture: PictureOfDayEntity)

    @Query("SELECT dateTaken FROM daily_pictures ORDER BY dateTaken DESC LIMIT 1")
    suspend fun getLastDayFetched(): LocalDate?

    @Query("DELETE FROM daily_pictures WHERE dateTaken < :exclusiveDate")
    suspend fun deleteBefore(exclusiveDate: LocalDate)

}