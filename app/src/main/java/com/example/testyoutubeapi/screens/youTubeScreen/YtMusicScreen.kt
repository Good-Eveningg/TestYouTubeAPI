package com.example.testyoutubeapi.screens.youTubeScreen


import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.ui.theme.*
import com.google.android.exoplayer2.ui.StyledPlayerView


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
    val searchWidgetState by youTubeScreenViewModel.searchWidgetState
    val searchTextState by youTubeScreenViewModel.searchTextState
    val videoChosed by youTubeScreenViewModel.videImported.observeAsState()
    val onPlayerClicked by youTubeScreenViewModel.onPlayerClicked.observeAsState()


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp)
    ) {
        if (onPlayerClicked == true) {
            BigPlayer(
                youTubeScreenViewModel = youTubeScreenViewModel,
                onPlayClicked = { youTubeScreenViewModel.playPauseVideo() },
                onBackClicked = { youTubeScreenViewModel.previousVideo() },
                onNextClicked = { youTubeScreenViewModel.nextVideo() },
                onTurnButtonClicked = { youTubeScreenViewModel.onPlayerClicked.postValue(false) })

        } else {
            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchTextState = searchTextState,
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
            ) {

                Column(modifier = Modifier.background(primaryBlack)) {
                    if (searchWidgetState == SearchWidgetState.CLOSED) {
                        namePlayListForRow?.let { SetRowPlayListTitle(rowPlayListName = it) }
                        youTubePlayListRowItems?.let { it1 ->
                            PlayListRow(
                                it1,
                                onItemClicked = { youTubeScreenViewModel.setVideoId(it, 0) })
                        }
                        namePlayListForGrid?.let { SetGridPlayListTitle(gridPlayListName = it) }
                        youTubePlayListGridItems?.let { it1 ->
                            PlayListGrid(
                                gridPlayList = it1,
                                onItemClicked = { youTubeScreenViewModel.setVideoId(it, 1) })
                        }
                        if (videoChosed == true) {
                            SmallPlayerView(youTubeScreenViewModel = youTubeScreenViewModel,
                                onPlayClicked = {
                                    youTubeScreenViewModel.playPauseVideo()

                                },
                                onBackClicked = { youTubeScreenViewModel.previousVideo() },
                                onNextClicked = { youTubeScreenViewModel.nextVideo() },
                                onProgressChanged = {},
                                onPlayerClicked = {youTubeScreenViewModel.onPlayerClicked.postValue(true)})
                        }

                    } else {
                        searchRequestResult?.let { it1 ->
                            SearchResponse(it1) {
                                youTubeScreenViewModel.setVideoId(it,2)
                            }
                        }
                        if (videoChosed == true) {
                            SmallPlayerView(youTubeScreenViewModel = youTubeScreenViewModel,
                                onPlayClicked = {
                                    youTubeScreenViewModel.playPauseVideo()
                                },
                                onBackClicked = { },
                                onNextClicked = { }, onProgressChanged = {},
                                onPlayerClicked = {youTubeScreenViewModel.onPlayerClicked.postValue(true)})
                        }
                    }

                }

            }
        }
    }
}


@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    clearSearchRequest: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked,
                clearSearchRequest = clearSearchRequest
            )
        }
    }
}


@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = primaryBlack,
        title = {
            Text(
                text = ""
            )
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White
                )
            }
        }
    )
}


@Composable
fun SmallPlayerView(
    youTubeScreenViewModel: YouTubeScreenViewModel,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onProgressChanged: (Float) -> Unit,
    onPlayerClicked: () -> Unit
) {
    val progress by youTubeScreenViewModel.videoDurationProgress.observeAsState()
    val currentItem by youTubeScreenViewModel.currentItem.observeAsState()
    val playerState by youTubeScreenViewModel.isPlayerPlaying.observeAsState()
    val exoPlayer = youTubeScreenViewModel.getPlayer()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)

    ) {
        progress?.let {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = progress!!,
                onValueChange = { progress -> onProgressChanged(progress) },
                valueRange = 0f..1f,
                colors =
                SliderDefaults.colors(
                    thumbColor = primaryGrey,
                    activeTickColor = primaryWhite
                )
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(98.dp),
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
            IconButton(modifier = Modifier.padding(end = 10.dp), onClick = { onBackClicked() }) {
                Icon(
                    painterResource(R.drawable.back_button),
                    contentDescription = "Back button",
                    tint = primaryWhite
                )
            }

            IconButton(modifier = Modifier.padding(end = 10.dp), onClick = { onPlayClicked() }) {
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
            IconButton(modifier = Modifier.padding(end = 10.dp), onClick = { onNextClicked() }) {
                Icon(
                    painterResource(R.drawable.next_button),
                    contentDescription = "Back button",
                    tint = primaryWhite
                )
            }
        }
    }
}

@Composable
fun BigPlayer(
    youTubeScreenViewModel: YouTubeScreenViewModel,
    onPlayClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onTurnButtonClicked: () -> Unit
) {
    val progress by youTubeScreenViewModel.videoDurationProgress.observeAsState()
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
                textAlign = TextAlign.Center
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
                progress?.let {
                    Slider(
                        modifier = Modifier.fillMaxWidth(),
                        value = it,
                        onValueChange = { progress: Float -> youTubeScreenViewModel.videoDurationProgress.postValue(progress) },
                        valueRange = 0f..1f,
                        colors =
                        SliderDefaults.colors(
                            thumbColor = primaryGrey,
                            activeTickColor = primaryWhite
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    clearSearchRequest: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = searchGray
    ) {
        TextField(modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = primaryWhite
                )
            },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = primaryWhite
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                            clearSearchRequest()
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = primaryWhite
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    if (keyboardController != null) {
                        keyboardController.hide()
                    }
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = searchGray,
                cursorColor = primaryWhite.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@Composable
fun SearchResponse(
    searchItem: List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>,
    itemClicked: (Int) -> Unit
) {
    LazyColumn() {
        items(searchItem) {
            Surface(
                modifier = Modifier.clickable { itemClicked(searchItem.indexOf(it)) },
                color = primaryBlack
            ) {
                SearchResponseItem(searchItem = it)
            }
        }
    }
}


@Composable
fun SearchResponseItem(searchItem: com.example.testyoutubeapi.models.retrofit.searchRequest.Item) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(searchItem.snippet.thumbnails.default.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Column {
            Text(
                text = searchItem.snippet.title,
                color = primaryWhite,
                fontSize = 16.sp
            )
            Text(
                text = searchItem.snippet.channelTitle,
                color = primaryWhite,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun SetRowPlayListTitle(rowPlayListName: String) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .padding(start = 40.dp, top = 9.dp)
    ) {
        Text(
            text = rowPlayListName,
            color = primaryWhite,
            fontSize = 24.sp,

            )
    }
}

@Composable
fun SetGridPlayListTitle(gridPlayListName: String) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .verticalScroll(rememberScrollState())
            .padding(start = 40.dp, top = 4.dp)
    )
    {
        Text(
            text = gridPlayListName,
            color = primaryWhite,
            fontSize = 24.sp,
        )
    }
}

@Composable
fun MusicListRowItem(rowItem: Item) {
    Column(
        modifier = Modifier
            .padding(end = 14.dp)
            .size(200.dp, 312.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(rowItem.snippet.thumbnails.maxres.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 212.dp, height = 200.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = rowItem.snippet.title,
            Modifier
                .padding(3.dp)
                .align(CenterHorizontally),
            color = primaryWhite

        )
        Text(
            text = rowItem.snippet.channelTitle,
            modifier = Modifier.align(CenterHorizontally),
            color = primaryGrey
        )
    }
}

@Composable
fun MusicListGridItem(gridItem: Item) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .size(100.dp, 101.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(gridItem.snippet.thumbnails.default.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .height(69.dp)
        )
        Text(
            text = gridItem.snippet.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterHorizontally),
            color = primaryWhite,
            fontSize = 6.sp
        )
        Text(
            text = gridItem.snippet.channelTitle,
            textAlign = TextAlign.Center,
            color = primaryGrey,
            modifier = Modifier.align(CenterHorizontally),
            fontSize = 6.sp
        )
    }
}

@Composable
fun PlayListRow(rowPlayList: List<Item>, onItemClicked: (Int) -> Unit) {
    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(start = 20.dp)
    ) {
        items(rowPlayList) {
            Surface(
                modifier = Modifier.clickable { onItemClicked(rowPlayList.indexOf(it)) },
                color = primaryBlack
            ) {
                MusicListRowItem(rowItem = it)
            }
        }
    }
}

@Composable
fun PlayListGrid(gridPlayList: List<Item>, onItemClicked: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .height(270.dp)
            .padding(top = 7.dp)
            .padding(start = 20.dp)
    ) {
        items(gridPlayList) {
            Surface(
                modifier = Modifier.clickable { onItemClicked(gridPlayList.indexOf(it)) },
                color = primaryBlack
            ) {
                MusicListGridItem(gridItem = it)
            }
        }
    }
}
