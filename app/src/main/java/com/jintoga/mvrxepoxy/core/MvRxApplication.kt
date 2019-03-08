package com.jintoga.mvrxepoxy.core

import android.app.Application
import org.koin.android.ext.android.startKoin

class MvRxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(apiServiceModule))
    }

}