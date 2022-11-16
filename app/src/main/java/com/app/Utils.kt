package com.app

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import timber.log.Timber


object Utils {


    fun isNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        Timber.tag("networkConnection").d( "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }
}