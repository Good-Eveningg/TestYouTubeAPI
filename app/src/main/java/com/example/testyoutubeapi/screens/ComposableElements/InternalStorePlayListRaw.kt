package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryDark
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun InternalStorePlayListRaw(
    mediaList: List<LocalStorageAudioModel>,
    onItemClicked: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(bottom = 3.dp)) {
        items(mediaList) {
            Surface(
                modifier = Modifier.clickable { onItemClicked(mediaList.indexOf(it)) },
                color = primaryBlack
            ) {
                InternalStorePlayListRawItem(mediaItem = it)
            }
        }
    }
}

@Composable
fun InternalStorePlayListRawItem(mediaItem: LocalStorageAudioModel) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryBlack)
            .height(74.dp)
            .padding(start = 14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(47.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(primaryDark)
        ) {
            Image(
                painterResource(R.drawable.internal_music_item),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)

            )
        }
        Column {
            Text(
                maxLines = 2,
                text = mediaItem.aName,
                color = primaryWhite,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                maxLines = 1,
                text = mediaItem.aArtist,
                color = primaryWhite,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 26.dp)
            )
        }
    }

}