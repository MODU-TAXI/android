package com.motax.modutaxi.data.config

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkManager @Inject constructor(private val connectivityManager: ConnectivityManager) {

    private val _isNetworkConnected = MutableStateFlow(false)
    val isNetworkConnected: StateFlow<Boolean> = _isNetworkConnected

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isNetworkConnected.value = true
        }

        override fun onLost(network: Network) {
            _isNetworkConnected.value = false
        }
    }

    fun startNetwork() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun endNetwork() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}