package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.testyoutubeapi.ui.theme.primaryBlack

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = primaryBlack,
        title = {
            Text(
                text = ""
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}