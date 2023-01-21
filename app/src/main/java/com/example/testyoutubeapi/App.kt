package com.example.testyoutubeapi

import android.app.Application
import com.example.testyoutubeapi.di.viewModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(viewModule)
        }
    }
}