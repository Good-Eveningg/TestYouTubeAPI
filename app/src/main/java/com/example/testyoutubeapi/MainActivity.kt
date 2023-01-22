package com.example.testyoutubeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.testyoutubeapi.screens.MainScreenView
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val youTubeScreenViewModel by viewModel<YouTubeScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val youTubePlayListItems by youTubeScreenViewModel.firstPlayList.observeAsState()
            youTubePlayListItems?.let { MainScreenView(firstListItem = it) }
        }
    }
}



