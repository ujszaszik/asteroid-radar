package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repository.AsteroidRepository

class AsteroidUpdateWorker @WorkerInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val asteroidRepository: AsteroidRepository
) : CoroutineWorker(context, params) {

    companion object {
        internal const val WORK_NAME = "Asteroid Update Worker"
    }

    override suspend fun doWork(): Result {
        return doWorkWhenConnected {
            asteroidRepository.fetchAsteroids()
            asteroidRepository.removeOutdatedAsteroids()
            asteroidRepository.fetchPictureOfTheDay()
            asteroidRepository.removeOutdatedPictures()
        }
    }
}