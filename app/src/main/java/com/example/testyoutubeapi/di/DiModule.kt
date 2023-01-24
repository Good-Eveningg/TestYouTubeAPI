package com.example.testyoutubeapi.di

import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { RetrofitRepo() }
    single { MyPlayer(androidContext()) }
}
val viewModule = module {
    viewModel { YouTubeScreenViewModel(get(), get()) }
    viewModel { InternalStoreScreenViewModel() }
}