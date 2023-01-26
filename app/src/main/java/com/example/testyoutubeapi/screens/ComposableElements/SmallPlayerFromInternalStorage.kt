package com.example.testyoutubeapi.screens.ComposableElements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite

@Composable
fun SmallPlayerViewFromInternalStorage(
    progress: Float,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onPlayerClicked: () -> Unit,
    mediaItem: LocalStorageAudioModel,
    playerState: Boolean
) {
    var _progress by remember {mutableStateOf(progress)}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
        ) {
            Slider(
                value = _progress,
                onValueChange = {
                    _progress = it
                },
                valueRange = 0f..1f,
                colors =
                SliderDefaults.colors(
                    thumbColor = primaryGrey,
                    activeTickColor = primaryWhite
                )
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Row() {
                Box(modifier = Modifier.size(72.dp)) {
                    Image(
                        painterResource(R.drawable.internal_music_item), contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center)
                    )
                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .width(173.dp)
                    .clickable { onPlayerClicked() }) {
                    Text(
                        maxLines = 2,
                        text = mediaItem.aName,
                        color = primaryWhite,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Text(
                        maxLines = 1,
                        text = mediaItem.aArtist,
                        color = primaryGrey,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 9.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = { onBackClicked() }) {
                        Icon(
                            painterResource(R.drawable.back_button),
                            contentDescription = "Back button",
                            tint = primaryWhite
                        )
                    }

                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = { onPlayClicked() }) {
                        Icon(
                            painterResource(
                                if (playerState == true) {
                                    R.drawable.pause_button
                                } else {
                                    R.drawable.play_button
                                }
                            ),
                            contentDescription = "Back button",
                            tint = primaryWhite
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(start = 10.dp),
                        onClick = { onNextClicked() }) {
                        Icon(
                            painterResource(R.drawable.next_button),
                            contentDescription = "Back button",
                            tint = primaryWhite
                        )
                    }
                }
            }
        }
    }
}