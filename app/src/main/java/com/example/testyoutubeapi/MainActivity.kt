package com.example.testyoutubeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testyoutubeapi.constValues.YOUTUBE_API_KEY
import com.example.testyoutubeapi.ui.theme.TestYoutubeAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestYoutubeAPITheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
                ) {
                    Greeting(YOUTUBE_API_KEY)
                    playListColumn()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun musicListItem() {
    Column() {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = ""
        )
        Text(text = "Sokol log obelsia bloh", Modifier.padding(3.dp))
        Text(text = "Sokol log obelsia bloh")
    }
}
@Composable
fun playListColumn(){
        LazyRow(){
        items(listOf("jd","dkjs","kjdsv")){
musicListItem()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestYoutubeAPITheme {
        Greeting("Android")

    }
}