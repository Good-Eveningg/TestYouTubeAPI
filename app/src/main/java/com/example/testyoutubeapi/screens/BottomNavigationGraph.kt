package com.example.testyoutubeapi.screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreen
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YtMusicScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationGraph(
    navController: NavHostController,
    youTubeScreenViewModel: YouTubeScreenViewModel,
    internalStoreScreenViewModel: InternalStoreScreenViewModel,
    context:Context
) {

    NavHost(navController, startDestination = BottomNavItem.YtMusicScreen.screen_route) {
        composable(BottomNavItem.YtMusicScreen.screen_route) {
            YtMusicScreen(youTubeScreenViewModel, context)
        }
        composable(BottomNavItem.InternalStoreScreen.screen_route) {
            InternalStoreScreen(internalStoreScreenViewModel = internalStoreScreenViewModel)
        }
    }
}