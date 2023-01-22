package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun YtMusicScreen(firstPlayList: List<Item>) {
    Column(modifier = Modifier.background(primaryBlack)) {
        SetHeaderText(playListName = "playListName")
        PlayListColumn(firstPlayList)
    }

}

@Composable
fun SetHeaderText(playListName:String){
    Text(text = playListName)
}

@Composable
fun MusicListItem(item: Item) {
    Column(modifier = Modifier
        .padding(horizontal = 20.dp)
        .size(272.dp, 424.dp)) {
        Image(
            painter = rememberAsyncImagePainter(item.snippet.thumbnails.maxres.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = item.snippet.title,
            Modifier
                .padding(3.dp)
                .align(CenterHorizontally),
            color = primaryWhite
        )
        Text(
            text = item.snippet.channelTitle,
            modifier = Modifier.align(CenterHorizontally),
            color = primaryGrey
        )
    }
}

@Composable
fun PlayListColumn(firstPlayList: List<Item>) {
    LazyRow {
        items(firstPlayList) {
            MusicListItem(item = it)
        }
    }
}





