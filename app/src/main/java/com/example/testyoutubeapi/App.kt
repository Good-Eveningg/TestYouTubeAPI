package com.example.testyoutubeapi

import android.app.Application
import com.example.testyoutubeapi.di.dataModule
import com.example.testyoutubeapi.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule,viewModule))
        }
    }
}