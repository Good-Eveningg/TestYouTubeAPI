package com.example.testyoutubeapi.bcReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.testyoutubeapi.constValues.APP
import com.example.testyoutubeapi.constValues.BACK_BUTTON
import com.example.testyoutubeapi.constValues.NEXT_BUTTON
import com.example.testyoutubeapi.constValues.PLAY_PAUSE_BUTTON

class MyReceiver : BroadcastReceiver() {
 private val youTubeScreenViewModel = APP.youTubeScreenViewModel
 private val internalStoreScreenViewModel = APP.internalStoreScreenViewModel
    override fun onReceive(context: Context?, intent: Intent?) {
        val backMessage = intent?.getStringExtra(BACK_BUTTON)
        val playPauseMessage = intent?.getStringExtra(PLAY_PAUSE_BUTTON)
        val nextMessage = intent?.getStringExtra(NEXT_BUTTON)
        if(backMessage != null){
            youTubeScreenViewModel.backVideo()
        }else if(playPauseMessage != null){
            youTubeScreenViewModel.playPauseVideo()
        }else if(nextMessage != null){
            youTubeScreenViewModel.nextVideo()
        }
    }
}