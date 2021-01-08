package com.abhay.shaadi

import android.app.Application
import android.os.StrictMode
import com.abhay.shaadi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule
            )
        }

    }

}