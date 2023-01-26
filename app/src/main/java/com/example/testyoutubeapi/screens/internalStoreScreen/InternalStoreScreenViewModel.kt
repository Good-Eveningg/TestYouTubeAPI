package com.example.testyoutubeapi.screens.internalStoreScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.screens.youTubeScreen.SearchWidgetState
import com.example.testyoutubeapi.utils.audioFileFetcher.AudioFileFetcherImpl

class InternalStoreScreenViewModel(
    private val audioFileFetcherImpl: AudioFileFetcherImpl,
    private val myPlayer: MyPlayer
) : ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState
    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _externalAudiosList = MutableLiveData<List<LocalStorageAudioModel>>()
    val externalAudiosList: LiveData<List<LocalStorageAudioModel>> = _externalAudiosList

    init {
        getExternalAudioFileList()
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
    private fun getExternalAudioFileList() {
        _externalAudiosList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }

}