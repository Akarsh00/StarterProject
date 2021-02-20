package com.aki.commonlib

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class Connectivity(context: Context) {
    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    enum class Status {
        WIFI, MOBILE, NOT_CONNECTED
    }

    @SuppressLint("MissingPermission")
    private fun status(): Status {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                return Status.WIFI
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                return Status.MOBILE
            }
        }
        return Status.NOT_CONNECTED
    }

    val isConnected: Boolean
        get() {
            val status = status()
            return status == Status.MOBILE || status == Status.WIFI
        }

    val isConnectivityWifi: Boolean
        get() = status() == Status.WIFI

    val isConnectivityMobile: Boolean
        get() = status() == Status.MOBILE

    fun hasInternetConnection(): Boolean {
        return status() != Status.NOT_CONNECTED
    }

}