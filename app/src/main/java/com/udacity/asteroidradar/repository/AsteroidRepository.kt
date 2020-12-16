package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asteroid.asModel
import com.udacity.asteroidradar.keys.Keys
import com.udacity.asteroidradar.model.*
import com.udacity.asteroidradar.network.AsteroidResultParser
import com.udacity.asteroidradar.network.AsteroidService
import com.udacity.asteroidradar.network.NetworkStatus
import org.json.JSONObject
import org.threeten.bp.LocalDate
import javax.inject.Inject

class AsteroidRepository @Inject constructor(
    private val asteroidService: AsteroidService,
    private val parser: AsteroidResultParser,
    private val database: AsteroidDatabase,
    private val cacheStrategy: CacheStrategy
) {

    private val asteroidEntities = database.asteroidDao().getAsteroids()
    val asteroids = Transformations.map(asteroidEntities) { it.asModel() }

    private val _asteroidListNetworkStatus = MutableLiveData<NetworkStatus>()
    val asteroidListNetworkStatus: LiveData<NetworkStatus> get() = _asteroidListNetworkStatus

    private val pictureOfTheDayEntity = database.pictureDao().getPictureOfThisDay()
    val pictureOfTheDay = Transformations.map(pictureOfTheDayEntity) { it?.toModel() }

    private val _asteroidPictureNetworkStatus = MutableLiveData<NetworkStatus>()
    val asteroidPictureNetworkStatus: LiveData<NetworkStatus> get() = _asteroidPictureNetworkStatus

    suspend fun fetchAsteroids() {
        val lastDayFetched =
            database.asteroidDao().getLastDayFetched() ?: LocalDate.now().previousDay()
        val dateRangeToFetch = cacheStrategy.getDaysToFetch(lastDayFetched)
        if (dateRangeToFetch != LocalDateRange.EMPTY_RANGE) {
            val asteroidEntities = getAsteroidsFromNetwork(dateRangeToFetch)?.asEntity()
            asteroidEntities?.let { database.asteroidDao().insertAsteroids(it) }
        }
    }

    private suspend fun getAsteroidsFromNetwork(dateRangeToFetch: LocalDateRange): List<Asteroid>? {
        return networkCall(_asteroidListNetworkStatus) {
            val asteroidMap = asteroidService.getAsteroids(
                dateRangeToFetch.start.asFormattedString(),
                dateRangeToFetch.end.asFormattedString(),
                Keys.apiKey()
            ) as Map<*, *>
            parser.parseAsteroidsJsonResult(JSONObject(asteroidMap), dateRangeToFetch)
        }
    }

    suspend fun removeOutdatedAsteroids() =
        IO { database.asteroidDao().deleteBefore(LocalDate.now().previousDay()) }

    suspend fun fetchPictureOfTheDay() {
        val lastEntryDate =
            database.pictureDao().getLastDayFetched() ?: LocalDate.now().previousDay()
        if (lastEntryDate != LocalDate.now()) {
            networkCall(_asteroidPictureNetworkStatus) {
                asteroidService.getImageOfTheDay(Keys.apiKey()).also {
                    database.pictureDao().insertPicture(it.toEntity())
                }
            }
        }
    }

    suspend fun removeOutdatedPictures() =
        IO { database.pictureDao().deleteBefore(LocalDate.now().minusWeek()) }
}