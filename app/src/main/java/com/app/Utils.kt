package com.app

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


object Utils {


    fun isNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        Timber.tag("networkConnection").d( "hasNetworkAvailable: ${(network != null)}")
        return (network != null)
    }


    fun formatTimeStamp(timestamp: Long) : String{
        return try {
            val sdf =  SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            val netDate = Date(timestamp * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}