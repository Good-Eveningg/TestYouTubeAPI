package com.example.testyoutubeapi.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.notificationManager.NotificationBuilder
import com.example.testyoutubeapi.notificationManager.NotificationManager
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import com.example.testyoutubeapi.utils.audioFileFetcher.AudioFileFetcherImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { RetrofitRepo() }
    single { MyPlayer(androidContext()) }
    single { AudioFileFetcherImpl(androidContext()) }
    single{NotificationManager(androidContext())}
    single{NotificationBuilder(androidContext())}

}
@RequiresApi(Build.VERSION_CODES.R)
val viewModule = module {
    viewModel { YouTubeScreenViewModel(get(), get(), get(),get()) }
    viewModel { InternalStoreScreenViewModel(get(),get(),get(),get()) }
}