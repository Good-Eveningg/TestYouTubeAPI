package com.example.testyoutubeapi.screens.internalStoreScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.screens.youTubeScreen.SearchWidgetState
import com.example.testyoutubeapi.utils.audioFileFetcher.AudioFileFetcherImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
class InternalStoreScreenViewModel(
    private val audioFileFetcherImpl: AudioFileFetcherImpl,
    private val myPlayer: MyPlayer
) : ViewModel() {

    private val _searchWidgetState = MutableLiveData<SearchWidgetState>(SearchWidgetState.CLOSED)
    val searchWidgetState: LiveData<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = MutableLiveData<String>("")
    val searchTextState: LiveData<String> = _searchTextState

    private val _externalAudiosList = MutableLiveData<List<LocalStorageAudioModel>>()
    val externalAudiosList: LiveData<List<LocalStorageAudioModel>> = _externalAudiosList

    private val _searchAudiosList = MutableLiveData<List<LocalStorageAudioModel>>()
    val searchAudiosList: LiveData<List<LocalStorageAudioModel>> = _searchAudiosList


    private val _onPlayerClicked = MutableLiveData<Boolean>()
    val onPlayerClicked: LiveData<Boolean> = _onPlayerClicked

    private val _itemImported = MutableLiveData<Boolean>()
    val itemImported: LiveData<Boolean> = _itemImported


    private val _currentItem = MutableLiveData<LocalStorageAudioModel>()
    val currentItem: LiveData<LocalStorageAudioModel> = _currentItem

    private val _onPlayPauseClicked = MutableLiveData(false)
    val onPlayPauseClicked: LiveData<Boolean> = _onPlayPauseClicked

    private val _progress: MutableLiveData<Long> = MutableLiveData()
    val progress: LiveData<Long> = _progress

    private val _duration: MutableLiveData<Long> = MutableLiveData()
    val duration: LiveData<Long> = _duration


    init {
        getExternalAudioFileList()
        updateProgress()
    }


    fun onPlayerClicked(bol: Boolean) {
        _onPlayerClicked.postValue(bol)
    }

    fun searchInPlayList(searchRequest: String) {

    }

    fun onPlayPauseClicked() {
        if (onPlayPauseClicked.value != true) {
            myPlayer.playVideoAudio()
            _onPlayPauseClicked.postValue( myPlayer.player.isPlaying)
        } else {
            myPlayer.pauseVideoAudio()
            _onPlayPauseClicked.postValue(myPlayer.player.isPlaying)
        }
    }

    private fun updateProgress() {
        viewModelScope.launch {
            while (true) {
                delay(200)
                if (itemImported.value == true) {
                    _progress.postValue(myPlayer.getProgress())
                }
                _duration.postValue(myPlayer.getDuration())
            }
        }
    }


    fun setDuration(progress: Float) {
        myPlayer.setProgress(progress)
    }

    fun importItemInPlayer(id: Int) {
        viewModelScope.launch() {
            _currentItem.postValue(_searchAudiosList.value?.get(id))
            _searchAudiosList.value?.get(id)?.let { myPlayer.setAudio(it.aPath) }
            _itemImported.postValue(true)
        }
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.postValue(newValue)
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.postValue(newValue)
    }


    fun previousVideo() {
        val currentItemPosition = currentItem.value?.let {
            searchAudiosList.value?.indexOf(
                it
            )
        }
        if (currentItemPosition != 0) {
            val previousItemId = currentItemPosition?.minus(1)
            if (previousItemId != null) {
                importItemInPlayer(previousItemId)
                _currentItem.postValue(searchAudiosList.value?.get(previousItemId))
                onPlayPauseClicked()
            }


        }
    }

    fun nextVideo() {
        val currentItemPosition = currentItem.value?.let {
            searchAudiosList.value?.indexOf(
                it
            )
        }
        if (currentItemPosition != (searchAudiosList.value?.size?.minus(1))) {
            val nextItemId = currentItemPosition?.plus(1)
            if (nextItemId != null) {
                importItemInPlayer(nextItemId)
                _currentItem.postValue(searchAudiosList.value?.get(nextItemId))
                onPlayPauseClicked()
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getExternalAudioFileList() {
        _searchAudiosList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }

}