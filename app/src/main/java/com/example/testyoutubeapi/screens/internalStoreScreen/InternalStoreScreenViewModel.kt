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
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
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

    private val _onPlayerClicked: MutableState<Boolean> = mutableStateOf(false)
    val onPlayerClicked: State<Boolean> = _onPlayerClicked
    private val _itemImported: MutableLiveData<Boolean> = MutableLiveData()
    val itemImported: LiveData<Boolean> = _itemImported
    private val _currentProgress: MutableState<Float> = mutableStateOf(value = 0f)
    val currentProgress: State<Float> = _currentProgress
    private val _progress: MutableState<Float> = mutableStateOf(value = 0f)
    private val _currentItem: MutableLiveData<LocalStorageAudioModel> = MutableLiveData()
    val currentItem: LiveData<LocalStorageAudioModel> = _currentItem
    private val _onPlayPauseClicked: MutableState<Boolean> = mutableStateOf(false)
    val onPlayPauseClicked: State<Boolean> = _onPlayPauseClicked


    init {
        getExternalAudioFileList()
    }

    fun updateProgress(progress: Float) {
        _progress.value = progress
    }

    fun onPlayerClicked(bol: Boolean) {
        _onPlayerClicked.value = bol
    }

    fun onPlayPauseClicked() {
        if (!onPlayPauseClicked.value) {
            myPlayer.playVideoAudio()
            _onPlayPauseClicked.value = myPlayer.player.isPlaying
        } else {
            myPlayer.pauseVideoAudio()
            _onPlayPauseClicked.value = myPlayer.player.isPlaying
        }
    }

    fun getProgress():Long{
        return myPlayer.getProgress()
    }
    fun getDuration():Long{
        return myPlayer.getDuration()
    }
    fun setDuration(progress:Float){
        myPlayer.setProgress(progress)
    }

    fun importItemInPlayer(id: Int) {
        viewModelScope.launch() {
            _currentItem.postValue(_externalAudiosList.value?.get(id))
            _externalAudiosList.value?.get(id)?.let { myPlayer.setAudio(it.aPath) }
            _itemImported.postValue(true)
        }
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun previousVideo() {
        val currentItemPosition = currentItem.value?.let {
            externalAudiosList.value?.indexOf(
                it
            )
        }
        if (currentItemPosition != 0) {
            val previousItemId = currentItemPosition?.minus(1)
            if (previousItemId != null) {
                importItemInPlayer(previousItemId)
                _currentItem.postValue(externalAudiosList.value?.get(previousItemId))
                _onPlayPauseClicked.value = myPlayer.player.isPlaying
            }


        }
    }

    fun nextVideo() {
        val currentItemPosition = currentItem.value?.let {
            externalAudiosList.value?.indexOf(
                it
            )
        }
        if (currentItemPosition != (externalAudiosList.value?.size?.minus(1))) {
            val nextItemId = currentItemPosition?.plus(1)
            if (nextItemId != null) {
                importItemInPlayer(nextItemId)
                _currentItem.postValue(externalAudiosList.value?.get(nextItemId))
                _onPlayPauseClicked.value = myPlayer.player.isPlaying
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getExternalAudioFileList() {
        _externalAudiosList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }

}