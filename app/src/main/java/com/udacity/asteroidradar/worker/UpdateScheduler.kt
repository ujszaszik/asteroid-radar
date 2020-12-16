package com.udacity.asteroidradar.worker

import android.content.Context
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UpdateScheduler @Inject constructor(private val context: Context) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    fun scheduleWork() {
        launch {
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                AsteroidUpdateWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                getWorkRequest()
            )
        }
    }

    private fun getWorkRequest(): PeriodicWorkRequest =
        createPeriodicDailyWorkRequest<AsteroidUpdateWorker>(getConstraints())

    private inline fun <reified W : ListenableWorker>
            createPeriodicDailyWorkRequest(constraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequest.Builder(W::class.java, 1L, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
    }

    private fun getConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    setRequiresDeviceIdle(true)
            }.build()
    }

}