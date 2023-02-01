package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun YTPlayListRow(rowPlayList: List<Item>, onItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(start = 20.dp)
    ) {
        items(rowPlayList) {
            Surface(
                modifier = Modifier.clickable { onItemClicked(rowPlayList.indexOf(it)) },
                color = primaryBlack
            ) {
                YTMusicListRowItem(rowItem = it)
            }
        }
    }
}

@Composable
fun YTMusicListRowItem(rowItem: Item) {
    Column(
        modifier = Modifier
            .padding(end = 14.dp)
            .size(200.dp, 312.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(rowItem.snippet.thumbnails.maxres.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 212.dp, height = 200.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = rowItem.snippet.title,
            Modifier
                .padding(3.dp)
                .align(Alignment.CenterHorizontally),
            color = primaryWhite

        )
        Text(
            text = rowItem.snippet.videoOwnerChannelTitle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = primaryGrey
        )
    }
}