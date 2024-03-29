package com.example.testyoutubeapi.screens.ComposableElements

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun SmallPlayerViewForYouTube(
    youTubeScreenViewModel: YouTubeScreenViewModel,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onPlayerClicked: () -> Unit
) {
    val progress by youTubeScreenViewModel.videoProgress.observeAsState()
    val duration by youTubeScreenViewModel.videoDuration.observeAsState()
    val currentItem by youTubeScreenViewModel.currentItem.observeAsState()
    val playerState by youTubeScreenViewModel.isPlayerPlaying.observeAsState()
    val exoPlayer = youTubeScreenViewModel.getPlayer()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = progress?.toFloat() ?: 0f,
            onValueChange = { youTubeScreenViewModel.setProgress(it) },
            valueRange = 0f..(duration?.toFloat() ?: 1f),
            colors =
            SliderDefaults.colors(
                thumbColor = primaryGrey,
                activeTickColor = primaryWhite
            )
        )

        Row(modifier = Modifier.fillMaxWidth()) {

            AndroidView(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
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
            Column(modifier = Modifier
                .width(160.dp)
                .weight(2f)
                .clickable { onPlayerClicked() }) {

                currentItem?.snippet?.let {
                    Text(
                        text = it.title,
                        color = primaryWhite
                    )
                }
                currentItem?.snippet?.let {
                    Text(
                        text = it.channelTitle,
                        color = primaryWhite
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(2f)) {
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
