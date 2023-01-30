package com.example.testyoutubeapi.screens.internalStoreScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testyoutubeapi.screens.ComposableElements.BigPlayerForInternalStorage
import com.example.testyoutubeapi.screens.ComposableElements.InternalStorePlayListRaw
import com.example.testyoutubeapi.screens.ComposableElements.MainAppBar
import com.example.testyoutubeapi.screens.ComposableElements.SmallPlayerViewFromInternalStorage
import com.example.testyoutubeapi.screens.youTubeScreen.SearchWidgetState
import com.example.testyoutubeapi.ui.theme.primaryBlack


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InternalStoreScreen(internalStoreScreenViewModel: InternalStoreScreenViewModel) {
    val internalStorePlayList by internalStoreScreenViewModel.externalStorageAudioList.observeAsState()
    val searchWidgetState by internalStoreScreenViewModel.searchWidgetState.observeAsState()
    val searchTextState by internalStoreScreenViewModel.searchTextState.observeAsState()
    val itemImported by internalStoreScreenViewModel.itemImported.observeAsState()
    val currentItem by internalStoreScreenViewModel.currentItem.observeAsState()
    val audiPlaying by internalStoreScreenViewModel.onPlayPauseClicked.observeAsState()
    val onPlayerClicked by internalStoreScreenViewModel.onPlayerClicked.observeAsState()
    val searchAudioList by internalStoreScreenViewModel.searchedAudioList.observeAsState()


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {

        if (onPlayerClicked == false) {
            Scaffold(
                topBar = {
                    searchWidgetState?.let {
                        searchTextState?.let { it1 ->
                            MainAppBar(
                                searchWidgetState = it,
                                searchTextState = it1,
                                onTextChange = {
                                    internalStoreScreenViewModel.updateSearchTextState(newValue = it)
                                },
                                onCloseClicked = {
                                    internalStoreScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                },
                                onSearchClicked = {
                                    internalStoreScreenViewModel.searchInPlayList(it)
                                },
                                onSearchTriggered = {
                                    internalStoreScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                                }, clearSearchRequest = {
                                    internalStoreScreenViewModel.setNullValueToSearchList()
                                    internalStoreScreenViewModel.setPlayListType(0)
                                }
                            )
                        }
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(primaryBlack)
                ) {
                    if (searchWidgetState == SearchWidgetState.CLOSED) {
                        Box(modifier = Modifier.weight(5f)) {
                            internalStorePlayList?.let { it1 ->
                                InternalStorePlayListRaw(
                                    mediaList = it1,
                                    onItemClicked = {
                                        internalStoreScreenViewModel.importItemInPlayer(it)
                                    })
                            }
                        }
                    } else {
                        Box(modifier = Modifier.weight(5f)) {
                            searchAudioList?.let { it1 ->
                                InternalStorePlayListRaw(
                                    mediaList = it1,
                                    onItemClicked = {
                                        internalStoreScreenViewModel.importItemInPlayer(
                                            it
                                        )
                                    })
                            }
                        }
                    }

                    if (itemImported == true) {
                        Box(modifier = Modifier.weight(1f)) {
                            currentItem?.let { it1 ->
                                audiPlaying?.let { it2 ->
                                    SmallPlayerViewFromInternalStorage(
                                        internalStoreScreenViewModel,
                                        onPlayClicked = {
                                            internalStoreScreenViewModel.onPlayPauseClicked()
                                        },
                                        onBackClicked = { internalStoreScreenViewModel.previousVideo() },
                                        onNextClicked = { internalStoreScreenViewModel.nextVideo() },
                                        onPlayerClicked = {
                                            internalStoreScreenViewModel.onPlayerClicked(
                                                true
                                            )
                                        },
                                        mediaItem = it1,
                                        playerState = it2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            BigPlayerForInternalStorage(
                onPlayClicked = { internalStoreScreenViewModel.onPlayPauseClicked() },
                onBackClicked = { internalStoreScreenViewModel.previousVideo() },
                onNextClicked = { internalStoreScreenViewModel.nextVideo() },
                onTurnButtonClicked = { internalStoreScreenViewModel.onPlayerClicked(false) },
                internalStoreScreenViewModel = internalStoreScreenViewModel
            )
        }

    }
}


