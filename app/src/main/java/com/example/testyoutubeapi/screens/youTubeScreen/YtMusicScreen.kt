package com.example.testyoutubeapi.screens.youTubeScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    youTubeScreenViewModel: YouTubeScreenViewModel
) {
    val youTubePlayListRowItems by youTubeScreenViewModel.playListForRow.observeAsState()
    val youTubePlayListGridItems by youTubeScreenViewModel.playListForGrid.observeAsState()
    val namePlayListForRow by youTubeScreenViewModel.namePlayListForRow.observeAsState()
    val namePlayListForGrid by youTubeScreenViewModel.namePlayListForGrid.observeAsState()
    val searchRequestResult by youTubeScreenViewModel.searchRequestResult.observeAsState()
    val searchWidgetState by youTubeScreenViewModel.searchWidgetState.observeAsState()
    val searchTextState by youTubeScreenViewModel.searchTextState.observeAsState()
    val videoSelected by youTubeScreenViewModel.videoImportedInYoutube.observeAsState()
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
                onTurnButtonClicked = { youTubeScreenViewModel.setPlayerState(false) })

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
                                    youTubeScreenViewModel.clearSearchList()
                                }
                            )
                        }
                    }
                }
            ) {

                Column(modifier = Modifier.background(primaryBlack)) {
                    if (searchWidgetState == SearchWidgetState.CLOSED) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                            namePlayListForRow?.let { PlayListTitle(playListName = it) }}
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)){
                            youTubePlayListRowItems?.let { it1 ->
                                YTPlayListRow(
                                    it1,
                                    onItemClicked = {
                                        youTubeScreenViewModel.setVideoId(it, 0)

                                    })
                            }
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f).padding(top = 5.dp)) {
                            namePlayListForGrid?.let { PlayListTitle(playListName = it) }}
                        Box(modifier = Modifier.fillMaxWidth().weight(4f)){
                            youTubePlayListGridItems?.let { it1 ->
                                YTPlayListGrid(
                                    gridPlayList = it1,
                                    onItemClicked = {
                                        youTubeScreenViewModel.setVideoId(it, 1)
                                    })
                            }
                        }
                        if (videoSelected == true) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)) {
                                SmallPlayerViewForYouTube(youTubeScreenViewModel = youTubeScreenViewModel,
                                    onPlayClicked = {
                                        youTubeScreenViewModel.playPauseVideo()

                                    },
                                    onBackClicked = { youTubeScreenViewModel.backVideo() },
                                    onNextClicked = { youTubeScreenViewModel.nextVideo() },
                                    onPlayerClicked = {
                                        youTubeScreenViewModel.setPlayerState(
                                            true
                                        )
                                    })
                            }
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
                                onNextClicked = { },
                                onPlayerClicked = {
                                    youTubeScreenViewModel.setPlayerState(
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










