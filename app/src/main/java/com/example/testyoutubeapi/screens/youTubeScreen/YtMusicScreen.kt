package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.ui.theme.primaryBlack
import com.example.testyoutubeapi.ui.theme.primaryGrey
import com.example.testyoutubeapi.ui.theme.primaryWhite
import com.example.testyoutubeapi.ui.theme.searchGray

@Composable
fun YtMusicScreen(
youTubeScreenViewModel: YouTubeScreenViewModel
) {
    val youTubePlayListRowItems by youTubeScreenViewModel.playListForRow.observeAsState()
    val youTubePlayListGridItems by youTubeScreenViewModel.playListForGrid.observeAsState()
    val namePlayListForRow by youTubeScreenViewModel.namePlayListForRow.observeAsState()
    val namePlayListForGrid by youTubeScreenViewModel.namePlayListForGrid.observeAsState()

    Column(modifier = Modifier.background(primaryBlack)) {

        namePlayListForRow?.let { SetRowPlayListTitle(rowPlayListName = it) }
        youTubePlayListRowItems?.let { PlayListRow(it) }
        namePlayListForGrid?.let { SetGridPlayListTitle(gridPlayListName = it) }
        youTubePlayListGridItems?.let { PlayListGrid(gridPlayList = it) }
    }

}



@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
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
            }, textStyle = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize),
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
            }, trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
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
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = searchGray,
                cursorColor = primaryWhite.copy(alpha = ContentAlpha.medium)
            ))
    }
}



@Composable
fun SetRowPlayListTitle(rowPlayListName: String) {
    Text(
        text = rowPlayListName,
        color = primaryWhite
    )
}

@Composable
fun SetGridPlayListTitle(gridPlayListName: String) {
    Text(
        text = gridPlayListName,
        color = primaryWhite
    )
}

@Composable
fun MusicListRowItem(rowItem: Item) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .size(272.dp, 424.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(rowItem.snippet.thumbnails.maxres.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
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
            .padding(horizontal = 14.dp)
            .size(70.dp, 70.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(gridItem.snippet.thumbnails.default.url),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(height = 40.dp, width = 54.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = gridItem.snippet.title,
            Modifier.align(CenterHorizontally),
            color = primaryWhite,
            fontSize = 6.sp
        )
        Text(
            text = gridItem.snippet.channelTitle,
            modifier = Modifier.align(CenterHorizontally),
            color = primaryGrey,
            fontSize = 6.sp
        )
    }
}

@Composable
fun PlayListRow(rowPlayList: List<Item>) {
    LazyRow {
        items(rowPlayList) {
            MusicListRowItem(rowItem = it)
        }
    }
}

@Composable
fun PlayListGrid(gridPlayList: List<Item>) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(gridPlayList) {
            MusicListGridItem(gridItem = it)
        }
    }
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}







