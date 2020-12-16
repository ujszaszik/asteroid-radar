package com.udacity.asteroidradar.worker

import androidx.work.ListenableWorker
import com.udacity.asteroidradar.network.NetworkUtil

suspend fun doWorkWhenConnected(block: suspend () -> Any): ListenableWorker.Result {
    return if (NetworkUtil.isConnected) {
        try {
            block.invoke()
            ListenableWorker.Result.success()
        } catch (e: Throwable) {
            ListenableWorker.Result.failure()
        }
    } else ListenableWorker.Result.failure()
}