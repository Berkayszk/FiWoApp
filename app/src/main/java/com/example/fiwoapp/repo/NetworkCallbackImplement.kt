package com.example.fiwoapp.repo

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkCallbackImplement(
    private val onNetworkAvailable: () -> Unit,
    private val onNetworkLost: () -> Unit
) : ConnectivityManager.NetworkCallback() {

    private var isConnected = false // İnternet bağlantısının durumunu izlemek için bir bayrak kullanın

    override fun onAvailable(network: Network) {
        // İnternet bağlantısı sağlandığında bu işlev çağrılır
        if (!isConnected) {
            // İnternet bağlantısı daha önce kaybolmuşsa, verileri yükle
            onNetworkAvailable()
            isConnected = true // İnternet bağlantısı olduğunu işaretle
        }
    }

    override fun onLost(network: Network) {
        // İnternet bağlantısı kaybolduğunda bu işlev çağrılır
        onNetworkLost()
        isConnected = false // İnternet bağlantısı kaybolduğunu işaretle
    }
}