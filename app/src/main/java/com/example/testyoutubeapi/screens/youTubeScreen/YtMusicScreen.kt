package com.example.testyoutubeapi.screens.youTubeScreen


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testyoutubeapi.screens.ComposableElements.*
import com.example.testyoutubeapi.ui.theme.primaryBlack


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun YtMusicScreen(
    youTubeScreenViewModel: YouTubeScreenViewModel, context: Context
) {
    val youTubePlayListRowItems by youTubeScreenViewModel.playListForRow.observeAsState()
    val youTubePlayListGridItems by youTubeScreenViewModel.playListForGrid.observeAsState()
    val namePlayListForRow by youTubeScreenViewModel.namePlayListForRow.observeAsState()
    val namePlayListForGrid by youTubeScreenViewModel.namePlayListForGrid.observeAsState()
    val searchRequestResult by youTubeScreenViewModel.searchRequestResult.observeAsState()
    val searchWidgetState by youTubeScreenViewModel.searchWidgetState.observeAsState()
    val searchTextState by youTubeScreenViewModel.searchTextState.observeAsState()
    val videoSelected by youTubeScreenViewModel.videImported.observeAsState()
    val onPlayerClicked by youTubeScreenViewModel.onPlayerClicked.observeAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {
        if (onPlayerClicked == true) {
            BigPlayerForYouTube(
                youTubeScreenViewModel = youTubeScreenViewModel,
                onPlayClicked = { youTubeScreenViewModel.playPauseVideo() },
                onBackClicked = { youTubeScreenViewModel.backVideo() },
                onNextClicked = { youTubeScreenViewModel.nextVideo() },
                onTurnButtonClicked = { youTubeScreenViewModel.onPlayerClicked.postValue(false) })

        } else {
            Scaffold(
                topBar = {
                    searchWidgetState?.let {
                        searchTextState?.let { it1 ->
                            MainAppBar(
                                searchWidgetState = it,
                                searchTextState = it1,
                                onTextChange = {
                                    youTubeScreenViewModel.updateSearchTextState(newValue = it)
                                },
                                onCloseClicked = {
                                    youTubeScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                },
                                onSearchClicked = {
                                    youTubeScreenViewModel.searchRequest(it)
                                },
                                onSearchTriggered = {
                                    youTubeScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                                }, clearSearchRequest = {
                                    youTubeScreenViewModel.searchRequestResult.postValue(emptyList())
                                }
                            )
                        }
                    }
                }
            ) {

                Column(modifier = Modifier.background(primaryBlack)) {
                    if (searchWidgetState == SearchWidgetState.CLOSED) {
                        namePlayListForRow?.let { PlayListTitle(playListName = it) }
                        youTubePlayListRowItems?.let { it1 ->
                            YTPlayListRow(
                                it1,
                                onItemClicked = {
                                    youTubeScreenViewModel.setVideoId(it, 0)
                                    youTubeScreenViewModel.createNotification()
                                })
                        }
                        namePlayListForGrid?.let { PlayListTitle(playListName = it) }
                        youTubePlayListGridItems?.let { it1 ->
                            YTPlayListGrid(
                                gridPlayList = it1,
                                onItemClicked = { youTubeScreenViewModel.setVideoId(it, 1)
                                youTubeScreenViewModel.createNotification()})
                        }
                        if (videoSelected == true) {
                            SmallPlayerViewForYouTube(youTubeScreenViewModel = youTubeScreenViewModel,
                                onPlayClicked = {
                                    youTubeScreenViewModel.playPauseVideo()

                                },
                                onBackClicked = { youTubeScreenViewModel.backVideo() },
                                onNextClicked = { youTubeScreenViewModel.nextVideo() },
                                onProgressChanged = {},
                                onPlayerClicked = {
                                    youTubeScreenViewModel.onPlayerClicked.postValue(
                                        true
                                    )
                                })
                        }

                    } else {
                        searchRequestResult?.let { it1 ->
                            SearchResponseFromYouTube(it1) {
                                youTubeScreenViewModel.setVideoId(it, 2)
                            }
                        }
                        if (videoSelected == true) {
                            SmallPlayerViewForYouTube(youTubeScreenViewModel = youTubeScreenViewModel,
                                onPlayClicked = {
                                    youTubeScreenViewModel.playPauseVideo()
                                },
                                onBackClicked = { },
                                onNextClicked = { }, onProgressChanged = {},
                                onPlayerClicked = {
                                    youTubeScreenViewModel.onPlayerClicked.postValue(
                                        true
                                    )
                                })
                        }
                    }

                }

            }
        }
    }
}










