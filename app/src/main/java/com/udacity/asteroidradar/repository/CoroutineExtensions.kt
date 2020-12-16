package com.udacity.asteroidradar.repository

import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.network.NetworkStatus
import com.udacity.asteroidradar.network.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> IO(block: suspend () -> T): T {
    return withContext(Dispatchers.IO) {
        block.invoke()
    }
}

suspend fun <T> networkCall(
    liveData: MutableLiveData<NetworkStatus>,
    block: suspend () -> T
): T? {
    var result: T? = null
    if (NetworkUtil.isConnected) {
        liveData.postValue(NetworkStatus.Loading)
        try {
            result = IO { block.invoke() }
            liveData.postValue(NetworkStatus.Success)
        } catch (t: Throwable) {
            liveData.postValue(NetworkStatus.Error(t))
        }
    }
    return result
}