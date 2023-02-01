package com.example.testyoutubeapi.screens.ComposableElements

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun BigPlayerForYouTube(
    youTubeScreenViewModel: YouTubeScreenViewModel,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onTurnButtonClicked: () -> Unit
) {
   val progress by youTubeScreenViewModel.videoProgress.observeAsState()
    val duration by youTubeScreenViewModel.videoDuration.observeAsState()
    val currentItem by youTubeScreenViewModel.currentItem.observeAsState()
    val playerState by youTubeScreenViewModel.isPlayerPlaying.observeAsState()
    val exoPlayer = youTubeScreenViewModel.getPlayer()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryBlack)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
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
                modifier =  Modifier.padding(start = 62.dp, top = 48.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(391.dp),
                factory = {
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                        layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                currentItem?.snippet?.let {
                    Text(
                        text = it.title,
                        color = primaryWhite,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                currentItem?.snippet?.let {
                    Text(
                        text = it.channelTitle,
                        color = primaryGrey,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    value = progress?.toFloat()?:0f,
                    onValueChange = { youTubeScreenViewModel.setProgress(it) },
                    valueRange = 0f..(duration?.toFloat() ?: 1f),
                    colors =
                    SliderDefaults.colors(
                        thumbColor = primaryGrey,
                        activeTickColor = primaryWhite
                    )
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    horizontalArrangement = Arrangement.Center) {
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
                        modifier = Modifier.padding(end = 10.dp),
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
                        modifier = Modifier.padding(end = 10.dp),
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