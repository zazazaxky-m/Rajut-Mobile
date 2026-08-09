package org.tubeskelompok1.rajutmobile

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.tubeskelompok1.rajutmobile.di.initKoin

class RajutApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@RajutApplication) }
    }
}
