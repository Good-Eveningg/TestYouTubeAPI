package com.example.testyoutubeapi.screens.ComposableElements

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BigPlayerForInternalStorage(
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onTurnButtonClicked: () -> Unit,
    internalStoreScreenViewModel: InternalStoreScreenViewModel
) {
    val playerState by internalStoreScreenViewModel.onPlayPauseClicked.observeAsState()
    val mediaItem by internalStoreScreenViewModel.currentItem.observeAsState()
    val progress by internalStoreScreenViewModel.progress.observeAsState()
    val duration by internalStoreScreenViewModel.duration.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryBlack)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .weight(1f)
        ) {
            IconButton(onClick = { onTurnButtonClicked() }) {
                Icon(
                    painterResource(R.drawable.turn_button),
                    contentDescription = "Turn button",
                    tint = primaryWhite,
                    modifier = Modifier.padding(start = 48.dp, top = 48.dp)
                )
            }
            Text(
                text = "Playing Now",
                fontSize = 20.sp,
                color = primaryGrey,
                modifier = Modifier.padding(start = 62.dp, top = 48.dp)
            )
        }
        Box(modifier = Modifier.weight(1f)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painterResource(R.drawable.internal_music_item), contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }

        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            mediaItem?.let {
                Text(
                    maxLines = 2,
                    text = it.aName,
                    color = primaryWhite,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            mediaItem?.let {
                Text(
                    maxLines = 1,
                    text = it.aArtist,
                    color = primaryGrey,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 9.dp)
                )
            }
            progress?.let {
                Slider(
                    modifier = Modifier.padding(top = 20.dp),
                    value = it.toFloat(),
                    onValueChange = {
                        internalStoreScreenViewModel.setDuration(it)
                    },
                    valueRange = 0f..(duration?.toFloat() ?: 1f),
                    colors =
                    SliderDefaults.colors(
                        thumbColor = primaryGrey,
                        activeTickColor = primaryWhite
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier.padding(end = 10.dp),
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