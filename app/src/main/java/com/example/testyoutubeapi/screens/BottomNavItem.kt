package com.example.testyoutubeapi.screens

import android.app.Activity
import android.app.Application
import com.example.testyoutubeapi.R
import java.util.Objects.toString

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object YtMusicScreen : BottomNavItem ("Музика", R.drawable.yt_music, "yt_music")
    object InternalStoreScreen : BottomNavItem ("Файли", R.drawable.internal_store, "internal_store")
}
