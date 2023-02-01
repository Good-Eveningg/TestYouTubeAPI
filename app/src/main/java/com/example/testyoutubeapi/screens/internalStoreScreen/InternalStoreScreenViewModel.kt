package com.example.testyoutubeapi.screens.internalStoreScreen

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.notificationManager.NotificationBuilder
import com.example.testyoutubeapi.notificationManager.NotificationManager
import com.example.testyoutubeapi.screens.youTubeScreen.SearchWidgetState
import com.example.testyoutubeapi.utils.audioFileFetcher.AudioFileFetcherImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("NewApi")
class InternalStoreScreenViewModel(
    private val audioFileFetcherImpl: AudioFileFetcherImpl,
    private val myPlayer: MyPlayer,
    private val notificationManager: NotificationManager,
    private val notificationBuilder: NotificationBuilder
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

    private val _itemImportedInInternalStorage = MutableLiveData<Boolean>()
    val itemImportedInInternalStorage: LiveData<Boolean> = _itemImportedInInternalStorage


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

    fun screenChangedToYouTube(){
        _itemImportedInInternalStorage.postValue(false)
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

    @SuppressLint("MissingPermission")
    fun createUpdateNotification(videoTitle: String, channelTitle: String) {
        viewModelScope.launch {
            notificationBuilder.playerState.postValue(onPlayPauseClicked.value)
            notificationManager.createNotificationChannel()
            notificationManager.notificationManager.notify(
                1,
                notificationBuilder.provideNotificationBuilder.setContentTitle(videoTitle)
                    .setContentText(channelTitle).build()
            )
        }
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
                delay(100)
                if (itemImportedInInternalStorage.value == true) {
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
                val item = _externalStorageAudioList.value?.get(id)
                _currentItem.postValue(item)
                if (item != null) {
                    myPlayer.setAudio(item.aPath)
                    createUpdateNotification(item.aName, item.aArtist)
                }
                _itemImportedInInternalStorage.postValue(true)
            } else {
                val item = _searchedAudioList.value?.get(id)
                _currentItem.postValue(item)
                if (item != null) {
                    myPlayer.setAudio(item.aPath)
                    createUpdateNotification(item.aName, item.aArtist)
                }
                _itemImportedInInternalStorage.postValue(true)
            }

        }
    }


    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.postValue(newValue)
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.postValue(newValue)
    }


    fun previousAudioItem() {
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
                    val item = externalStorageAudioList.value?.get(previousItemId)
                    if (item != null) {
                        _currentItem.postValue(item)
                        createUpdateNotification(item.aName, item.aArtist)
                    }
                    onPlayPauseClicked()
                }
            }
        }
    }

    fun nextAudioItem() {
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
                    val item = externalStorageAudioList.value?.get(nextItemId)
                    if (item != null) {
                        _currentItem.postValue(item)
                        createUpdateNotification(item.aName, item.aArtist)
                    }
                    onPlayPauseClicked()
                }


            }
        }
    }

    private fun getExternalAudioFileList() {
        _externalStorageAudioList.postValue(audioFileFetcherImpl.getAllAudioFromDevice())
    }
}