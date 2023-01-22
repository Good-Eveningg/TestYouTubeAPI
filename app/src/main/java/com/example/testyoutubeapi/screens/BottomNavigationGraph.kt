package com.example.testyoutubeapi.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreen
import com.example.testyoutubeapi.screens.youTubeScreen.YtMusicScreen

@Composable
fun NavigationGraph(navController: NavHostController, firstListItem: List<Item>) {
    NavHost(navController, startDestination = BottomNavItem.YtMusicScreen.screen_route) {
        composable(BottomNavItem.YtMusicScreen.screen_route) {
            YtMusicScreen(firstListItem)
        }
        composable(BottomNavItem.InternalStoreScreen.screen_route) {
            InternalStoreScreen()
        }
    }
}