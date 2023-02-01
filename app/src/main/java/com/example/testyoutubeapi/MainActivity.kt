package com.example.testyoutubeapi

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testyoutubeapi.bcReceiver.constValues.APP
import com.example.testyoutubeapi.screens.MainScreenView
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val youTubeScreenViewModel by viewModel<YouTubeScreenViewModel>()
    val internalStoreScreenViewModel by viewModel<InternalStoreScreenViewModel>()


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP = this
        setContent {
            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {
                MainScreenView(
                    youTubeScreenViewModel,
                    internalStoreScreenViewModel,
                    applicationContext
                )
            }
        }


    }
}