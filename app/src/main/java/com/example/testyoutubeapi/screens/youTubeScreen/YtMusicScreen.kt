package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.models.retrofit.getRequest.Item

@Composable
fun YtMusicScreen(firstPlayList: List<Item>) {
    Column {
        PlayListColumn(firstPlayList)
    }

}


@Composable
fun searchSongInYoutube() {

}

@Composable
fun MusicListItem(item: Item) {
    Column() {
        Image(
            painter = rememberAsyncImagePainter(item.snippet.thumbnails.default.url),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(text = item.snippet.title, Modifier.padding(3.dp))
        Text(text = item.snippet.channelTitle)
    }
}

@Composable
fun PlayListColumn(firstPlayList: List<Item>) {
    LazyRow() {
        items(firstPlayList) {
            MusicListItem(item = it)
        }
    }
}





