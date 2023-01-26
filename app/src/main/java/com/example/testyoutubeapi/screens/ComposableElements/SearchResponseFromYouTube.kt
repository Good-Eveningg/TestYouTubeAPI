package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun SearchResponseFromYouTube(
    searchResponseList: List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>,
    itemClicked: (Int) -> Unit
) {
    LazyColumn() {
        items(searchResponseList) {
            Surface(
                modifier = Modifier.clickable { itemClicked(searchResponseList.indexOf(it)) },
                color = primaryBlack
            ) {
                SearchResponseFromYouTubeItem(searchItem = it)
            }
        }
    }
}


@Composable
fun SearchResponseFromYouTubeItem(searchItem: com.example.testyoutubeapi.models.retrofit.searchRequest.Item) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(searchItem.snippet.thumbnails.default.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Column {
            Text(
                text = searchItem.snippet.title,
                color = primaryWhite,
                fontSize = 16.sp
            )
            Text(
                text = searchItem.snippet.channelTitle,
                color = primaryWhite,
                fontSize = 10.sp
            )
        }
    }
}