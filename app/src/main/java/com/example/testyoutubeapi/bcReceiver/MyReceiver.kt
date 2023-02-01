package com.example.testyoutubeapi.bcReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.testyoutubeapi.bcReceiver.constValues.APP
import com.example.testyoutubeapi.bcReceiver.constValues.BACK_BUTTON
import com.example.testyoutubeapi.bcReceiver.constValues.NEXT_BUTTON
import com.example.testyoutubeapi.bcReceiver.constValues.PLAY_PAUSE_BUTTON

class MyReceiver : BroadcastReceiver() {
    private val youTubeScreenViewModel = APP.youTubeScreenViewModel
    private val internalStoreScreenViewModel = APP.internalStoreScreenViewModel
    private val importedInYouTube: LiveData<Boolean> = youTubeScreenViewModel.videoImportedInYoutube
    private val importedInInternal: LiveData<Boolean> =
        internalStoreScreenViewModel.itemImportedInInternalStorage

    override fun onReceive(context: Context?, intent: Intent?) {
        val backMessage = intent?.getStringExtra(BACK_BUTTON)
        val playPauseMessage = intent?.getStringExtra(PLAY_PAUSE_BUTTON)
        val nextMessage = intent?.getStringExtra(NEXT_BUTTON)
        if (backMessage != null) {
            if (importedInYouTube.value == true) {
                youTubeScreenViewModel.backVideo()
            } else if (importedInInternal.value == true) {
                internalStoreScreenViewModel.previousAudioItem()
            }
        } else if (playPauseMessage != null) {
            if (importedInYouTube.value == true) {
                youTubeScreenViewModel.playPauseVideo()
            } else if (importedInInternal.value == true) {
                internalStoreScreenViewModel.onPlayPauseClicked()
            }
        } else if (nextMessage != null) {
            if (importedInYouTube.value == true) {
                youTubeScreenViewModel.nextVideo()
            } else if (importedInInternal.value == true) {
                internalStoreScreenViewModel.nextAudioItem()
            }
        }
    }
}