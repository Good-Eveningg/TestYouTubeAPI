package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun PlayListTitle(playListName: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp)
    ) {
        Text(maxLines = 1,
            text = playListName,
            color = primaryWhite,
            fontSize = 20.sp,
        )
    }
}