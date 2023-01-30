package com.example.testyoutubeapi.screens.internalStoreScreen

import android.os.Build
import androidx.annotation.RequiresApi
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

    private val _searchWidgetState = MutableLiveData(SearchWidgetState.CLOSED)
    val searchWidgetState: LiveData<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = MutableLiveData("")
    val searchTextState: LiveData<String> = _searchTextState

    private val _searchedAudioList = MutableLiveData<List<LocalStorageAudioModel>>()
    val searchedAudioList: LiveData<List<LocalStorageAudioModel>> = _searchedAudioList

    private val _externalStorageAudioList = MutableLiveData<List<LocalStorageAudioModel>>()
    val externalStorageAudioList: LiveData<List<LocalStorageAudioModel>> = _externalStorageAudioList


    private val _onPlayerClicked = MutableLiveData(false)
    val onPlayerClicked: LiveData<Boolean> = _onPlayerClicked

    private val _itemImported = MutableLiveData<Boolean>()
    val itemImported: LiveData<Boolean> = _itemImported


    private val _currentItem = MutableLiveData<LocalStorageAudioModel>()
    val currentItem: LiveData<LocalStorageAudioModel> = _currentItem

    private val _onPlayPauseClicked = MutableLiveData(false)
    val onPlayPauseClicked: LiveData<Boolean> = _onPlayPauseClicked

    private val _progress: MutableLiveData<Long> = MutableLiveData(0L)
    val progress: LiveData<Long> = _progress

    private val _duration: MutableLiveData<Long> = MutableLiveData(0L)
    val duration: LiveData<Long> = _duration

    private var playListId = 0


    init {
        getExternalAudioFileList()
        updateProgress()
    }


    fun onPlayerClicked(bol: Boolean) {
        _onPlayerClicked.postValue(bol)
    }

    fun setNullValueToSearchList() {
        _searchedAudioList.postValue(emptyList())
    }

    fun searchInPlayList(searchRequest: String) {
        val list = _externalStorageAudioList.value
        val match = list?.filter { it.aName.contains(searchRequest) }
        _searchedAudioList.postValue(match)
        setPlayListType(1)
    }

    fun setPlayListType(type: Int) {
        playListId = type
    }

    fun onPlayPauseClicked() {
        if (onPlayPauseClicked.value != true) {
            myPlayer.playVideoAudio()
            _onPlayPauseClicked.postValue(myPlayer.player.isPlaying)
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
                    _duration.postValue(myPlayer.getDuration())
                }
            }
        }
    }


    fun setDuration(progress: Float) {
        myPlayer.setProgress(progress)
    }

    fun importItemInPlayer(id: Int) {
        viewModelScope.launch() {
            if (playListId == 0) {
                _currentItem.postValue(_externalStorageAudioList.value?.get(id))
                _externalStorageAudioList.value?.get(id)?.let { myPlayer.setAudio(it.aPath) }
                _itemImported.postValue(true)
            } else {
                _currentItem.postValue(_searchedAudioList.value?.get(id))
                _searchedAudioList.value?.get(id)?.let { myPlayer.setAudio(it.aPath) }
                _itemImported.postValue(true)
            }

        }
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.postValue(newValue)
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.postValue(newValue)
    }


    fun previousVideo() {
        if (playListId == 0) {
            val currentItemPosition = currentItem.value?.let {
                externalStorageAudioList.value?.indexOf(
                    it
                )
            }
            if (currentItemPosition != 0) {
                val previousItemId = currentItemPosition?.minus(1)
                if (previousItemId != null) {
                    importItemInPlayer(previousItemId)
                    _currentItem.postValue(externalStorageAudioList.value?.get(previousItemId))
                    onPlayPauseClicked()
                }

            }
        }
    }

    fun nextVideo() {
        if (playListId == 0) {
            val currentItemPosition = currentItem.value?.let {
                externalStorageAudioList.value?.indexOf(
                    it
                )
            }
            if (currentItemPosition != (externalStorageAudioList.value?.size?.minus(1))) {
                val nextItemId = currentItemPosition?.plus(1)
                if (nextItemId != null) {
                    importItemInPlayer(nextItemId)
                    _currentItem.postValue(externalStorageAudioList.value?.get(nextItemId))
                    onPlayPauseClicked()
                }


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getExternalAudioFileList() {
        _externalStorageAudioList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }
}