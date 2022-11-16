package com.app.flobiz_assignment

import android.app.Application
import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
            Pluto.Installer(this)
                .addPlugin(PlutoNetworkPlugin("pluto_network"))
                .addPlugin(PlutoLoggerPlugin("logger"))
                .addPlugin(PlutoExceptionsPlugin("exception"))
                .install()
        }

    }
}