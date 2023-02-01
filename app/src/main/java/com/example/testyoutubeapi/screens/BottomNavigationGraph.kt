package com.example.testyoutubeapi.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

) {

    NavHost(navController, startDestination = BottomNavItem.YtMusicScreen.screen_route) {
        composable(BottomNavItem.YtMusicScreen.screen_route) {
            YtMusicScreen(youTubeScreenViewModel)
            internalStoreScreenViewModel.screenChangedToYouTube()
        }
        composable(BottomNavItem.InternalStoreScreen.screen_route) {
            InternalStoreScreen(internalStoreScreenViewModel = internalStoreScreenViewModel)
            youTubeScreenViewModel.screenChangedToInternal()
        }
    }
}