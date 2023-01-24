package com.example.testyoutubeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testyoutubeapi.screens.MainScreenView
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val youTubeScreenViewModel by viewModel<YouTubeScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Row(
                modifier = Modifier.background(Color.Black).fillMaxSize()
            ) {
                                MainScreenView(youTubeScreenViewModel, applicationContext)
                            }
                        }
                    }
                }




