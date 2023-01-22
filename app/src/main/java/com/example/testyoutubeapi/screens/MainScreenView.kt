package com.example.testyoutubeapi.screens

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.testyoutubeapi.models.retrofit.getRequest.Item

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenView(firstListItem: List<Item>) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        NavigationGraph(navController = navController, firstListItem = firstListItem)
    }
}