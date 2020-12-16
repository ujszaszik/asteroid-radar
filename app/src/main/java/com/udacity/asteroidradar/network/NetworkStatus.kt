package com.udacity.asteroidradar.network

sealed class NetworkStatus {

    object Loading : NetworkStatus()

    object Success : NetworkStatus()

    class Error(private val error: Throwable) : NetworkStatus() {
        fun getErrorMessage(): String? = error.message
    }
}