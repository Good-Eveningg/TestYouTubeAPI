package com.example.testyoutubeapi

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
import com.example.testyoutubeapi.notificationManager.DescriptionAdapter
import com.example.testyoutubeapi.screens.MainScreenView
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val youTubeScreenViewModel by viewModel<YouTubeScreenViewModel>()
    private val internalStoreScreenViewModel by viewModel<InternalStoreScreenViewModel>()


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Row(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {
                SideEffect {

                }
                MainScreenView(
                    youTubeScreenViewModel,
                    internalStoreScreenViewModel,
                    applicationContext
                )
            }
        }



    }
}