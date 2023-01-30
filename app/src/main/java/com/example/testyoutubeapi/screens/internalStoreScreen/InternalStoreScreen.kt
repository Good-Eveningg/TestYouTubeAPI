package com.example.testyoutubeapi.screens.internalStoreScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
    val internalStorePlayList by internalStoreScreenViewModel.externalAudiosList.observeAsState()
    val searchWidgetState by internalStoreScreenViewModel.searchWidgetState
    val searchTextState by internalStoreScreenViewModel.searchTextState
    val itemImported by internalStoreScreenViewModel.itemImported.observeAsState()
    val _progress by internalStoreScreenViewModel.currentProgress
    val currentItem by internalStoreScreenViewModel.currentItem.observeAsState()
    val audiPlaying by internalStoreScreenViewModel.onPlayPauseClicked
    val onPlayerClicked by internalStoreScreenViewModel.onPlayerClicked

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {

        if (onPlayerClicked == false) {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
                        onTextChange = {
                            internalStoreScreenViewModel.updateSearchTextState(newValue = it)
                        },
                        onCloseClicked = {
                            internalStoreScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        },
                        onSearchClicked = {

                        },
                        onSearchTriggered = {
                            internalStoreScreenViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }, clearSearchRequest = {
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(primaryBlack)
                ) {

                    Box(modifier = Modifier.weight(5f)) {
                        internalStorePlayList?.let { it1 ->
                            InternalStorePlayListRaw(
                                mediaList = it1,
                                onItemClicked = {
                                    internalStoreScreenViewModel.importItemInPlayer(it)
                                })
                        }
                    }

                    if (itemImported == true) {
                        Box(modifier = Modifier.weight(1f)) {
                            currentItem?.let { it1 ->
                                SmallPlayerViewFromInternalStorage(
                                    progress = _progress,
                                    onPlayClicked = { internalStoreScreenViewModel.onPlayPauseClicked() },
                                    onBackClicked = { internalStoreScreenViewModel.previousVideo() },
                                    onNextClicked = { internalStoreScreenViewModel.nextVideo() },
                                    onPlayerClicked = {
                                        internalStoreScreenViewModel.onPlayerClicked(
                                            true
                                        )
                                    },
                                    mediaItem = it1,
                                    playerState = audiPlaying
                                )
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


