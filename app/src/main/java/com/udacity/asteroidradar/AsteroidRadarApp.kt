package com.udacity.asteroidradar

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.udacity.asteroidradar.network.NetworkUtil
import com.udacity.asteroidradar.worker.UpdateScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AsteroidRadarApp : Application() {

    @Inject
    lateinit var updateScheduler: UpdateScheduler

    @Inject
    lateinit var networkUtil: NetworkUtil

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        networkUtil.registerNetworkCallback()
        updateScheduler.scheduleWork()
    }

}