package com.udacity.asteroidradar.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtil @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
) {

    companion object {
        @JvmStatic
        @Volatile
        var isConnected = false
    }

    fun registerNetworkCallback() {
        connectivityManager.registerNetworkCallback(networkRequest, getNetworkCallback())
    }

    private fun getNetworkCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                isConnected = false
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isConnected = true
            }
        }
    }

}