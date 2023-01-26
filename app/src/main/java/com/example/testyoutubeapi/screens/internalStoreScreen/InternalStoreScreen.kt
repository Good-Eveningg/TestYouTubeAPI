package com.example.testyoutubeapi.screens.internalStoreScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testyoutubeapi.screens.ComposableElements.InternalStorePlayListRaw
import com.example.testyoutubeapi.screens.ComposableElements.MainAppBar

import com.example.testyoutubeapi.screens.youTubeScreen.SearchWidgetState
import com.example.testyoutubeapi.ui.theme.primaryBlack

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InternalStoreScreen(internalStoreScreenViewModel: InternalStoreScreenViewModel) {
    val internalStorePlayList by internalStoreScreenViewModel.externalAudiosList.observeAsState()
    val searchWidgetState by internalStoreScreenViewModel.searchWidgetState
    val searchTextState by internalStoreScreenViewModel.searchTextState
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {

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
        ){
            internalStorePlayList?.let { it1 -> InternalStorePlayListRaw(mediaList = it1, onItemClicked = {}) }
        }

    }
}
