package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberAsyncImagePainter
import com.example.testyoutubeapi.models.retrofit.Item
import org.koin.androidx.viewmodel.ext.android.viewModel

class YouTubeScreen : Fragment() {
    val youTubeScreenViewModel by viewModel<YouTubeScreenViewModel>()

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Composable
    fun MusicListItem(item: Item) {
        Column() {
            Image(
                painter = rememberAsyncImagePainter(item.snippet.thumbnails.default),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Text(text = item.snippet.title, Modifier.padding(3.dp))
            Text(text = item.snippet.channelTitle)
        }
    }

    @Composable
    fun PlayListColumn(fistPlayList: List<Item>) {
        LazyRow() {
            fistPlayList.forEach { fistPlayList ->
                 }
        }
    }

}
}