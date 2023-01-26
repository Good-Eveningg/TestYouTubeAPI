package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun YTPlayListGrid(gridPlayList: List<Item>, onItemClicked: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .height(270.dp)
            .padding(top = 7.dp)
            .padding(start = 20.dp)
    ) {
        items(gridPlayList) {
            Surface(
                modifier = Modifier.clickable { onItemClicked(gridPlayList.indexOf(it)) },
                color = primaryBlack
            ) {
                YTMusicListGridItem(gridItem = it)
            }
        }
    }
}


@Composable
fun YTMusicListGridItem(gridItem: Item) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .size(100.dp, 101.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(gridItem.snippet.thumbnails.default.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .height(69.dp)
        )
        Text(
            text = gridItem.snippet.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = primaryWhite,
            fontSize = 6.sp
        )
        Text(
            text = gridItem.snippet.channelTitle,
            textAlign = TextAlign.Center,
            color = primaryGrey,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 6.sp
        )
    }
}