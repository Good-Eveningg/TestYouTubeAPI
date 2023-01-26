package com.example.testyoutubeapi.screens.internalStoreScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.utils.audioFileFetcher.AudioFileFetcherImpl

class InternalStoreScreenViewModel(
    private val audioFileFetcherImpl: AudioFileFetcherImpl,
    private val myPlayer: MyPlayer
) : ViewModel() {

    private val _externalAudiosList = MutableLiveData<List<LocalStorageAudioModel>>()
    val externalAudiosList: LiveData<List<LocalStorageAudioModel>> = _externalAudiosList

    init {
        getExternalAudioFileList()
    }

    private fun getExternalAudioFileList() {
        _externalAudiosList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }

}